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
result = []
callback_url = ""
@csrf_exempt
def compare(request):
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
        comparator = Comparator(curr_file, to_compare, result, lock, req)
        running_threads.append(comparator)
        comparator.start()

    pool = Pool()    # start a worker process
    pool.apply_async(wait_for_threads, args=(callback_url,), callback=post_result) # Evaluate "wait_for_threads()" async calling callback when done
    #return HttpResponse(json.dumps(result), mimetype='application/javascript')
    return HttpResponse('{status:200}', mimetype='application/javascript')

def wait_for_threads(callback_url):
    print "WAIT_FOR_THREADS"
    for t in running_threads:
        t.join()
    print "WAIT_FOR_THREADS FINISHED"
    return (result, callback_url)

def post_result(wait_for_threads_result):
    print "POST_RESULT"
    payload = wait_for_threads_result[0]
    callback_url = wait_for_threads_result[1]
    headers = {'content-type': 'application/json'}
    print "CALLBACK " + callback_url
    # POST to server 
    response = requests.post(callback_url, data=json.dumps(payload), headers=headers)

class Comparator(threading.Thread):
    def __init__(self, curr_file, to_compare, result, lock, req):
        self.curr_file = curr_file
        self.to_compare = to_compare
        self.result = result
        self.lock = lock
        self.req = req
        threading.Thread.__init__(self)
    
    def run(self):
        curr_file = self.curr_file
        to_compare = self.to_compare
        result = self.result
        similarities = []
        for tc in to_compare:
            curr_similarity = {'id' : tc['id'], 'similarity' : {}}
            if '1' in self.req['algorithms']:
                curr_similarity['similarity']['1'] = algorithms.ld_compare(curr_file['code'], tc['code'])
            if '2' in self.req['algorithms']:
                #print curr_file['code']
                #print tc['code']
                curr_similarity['similarity']['2'] = algorithms.sherlock_compare(curr_file['code'], tc['code'])
                #print curr_similarity['similarity']['2']
                
            #if '3' in self.req['algorithms']:
            #    curr_similarity['similarity']['3'] = algorithms.varch_compare(curr_file['code2'], tc['code2'])
            similarities.append(curr_similarity)
        self.lock.acquire()
        result.append({'id' : curr_file['id'], 'similarities' : similarities})
        self.lock.release()
