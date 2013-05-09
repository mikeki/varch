from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from multiprocessing import Pool
import ctypes
try: import simplejson as json
except ImportError: import json
import threading
# Monkey Patch to threading __DummyThread
threading._DummyThread._Thread__stop = lambda x: 42
import requests


#The evils of hardcoding the algorithms library! Put it on the settings NAO :O
algorithms = ctypes.cdll.LoadLibrary("../clib/libalgorithms.so")
algorithms.ld_compare.args = [ctypes.c_wchar_p, ctypes.c_wchar_p]
algorithms.sherlock_compare.args = [ctypes.c_wchar_p, ctypes.c_wchar_p]
#algorithms.varch_compare.args = [ctypes.c_wchar_p, ctypes.c_wchar_p]

# Returned data should have the following format in JSON:
#    [ 
#        { 'id' : id,
#          'similarities' : [
#                {'id' : id, 'similarity' : { <algo_id> : percent, <algo2_id> : percent2}},
#               ...
#          ]
#       }
#   ]
running_threads = []
callback_url = ""
@csrf_exempt
def compare(request):
    result = []
    req = json.loads(request.raw_post_data)['params']
    lock = threading.RLock()
    if 'files' not in req or 'algorithms' not in req or 'url' not in req:
        return HttpResponse(json.dumps({'error' : 'Wrong data: ' + str(req)}))
    files = req['files']
    callback_url = req['url']
    for f in files:
        #get rid off spaces and make it a c-valid parameter
        #keep a copy for the third algorithm which requires line jumps and such
        try:
            f['code'] = unicode(f['code'])
            f['code2'] = ctypes.c_wchar_p(f['code'])
            f['code'] = ctypes.c_wchar_p(" ".join(f['code'].split()))
        except Exception as (errno, errstr):
            print errstr
    if len(files) < 2: 
        return HttpResponse(json.dumps({'error' : 'Just one file: ' + str(req)}))
    for ci, curr_file in enumerate(files):
        to_compare = map(lambda p: p[1], filter(lambda x: x[0] > ci, enumerate(files)))
        if 'code' not in curr_file:
            return HttpResponse(json.dumps({'error' : 'Wrong data: ' + str(req)}))
        similarities = []
        comparison(curr_file, to_compare, req, result)

    headers = {'content-type': 'application/json'}
    print callback_url
    print json.dumps(result)
    response = requests.post(callback_url, data=json.dumps(result), headers=headers)
    return HttpResponse(status=200)

def comparison(curr_file, to_compare, req, result):
    similarities = []
    for tc in to_compare:
        curr_similarity = {'id' : tc['id'], 'similarity' : {}}
        if '1' in req['algorithms']:
            curr_similarity['similarity']['1'] = algorithms.ld_compare(curr_file['code'], tc['code'])
        if '2' in req['algorithms']:
            #print curr_file['code']
            #print tc['code']
            curr_similarity['similarity']['2'] = algorithms.sherlock_compare(curr_file['code'], tc['code'])
            #print curr_similarity['similarity']['2']

        #if '3' in self.req['algorithms']:
        #    curr_similarity['similarity']['3'] = algorithms.varch_compare(curr_file['code2'], tc['code2'])
        similarities.append(curr_similarity)
    result.append({'id' : curr_file['id'], 'similarities' : similarities})

