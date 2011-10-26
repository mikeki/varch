from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
import ctypes
try: import simplejson as json
except ImportError: import json
import threading

algorithms = ctypes.cdll.LoadLibrary("../clib/libalgorithms.so")
algorithms.ld_compare.args = [ctypes.c_char_p, ctypes.c_char_p]
algorithms.sherlock_compare.args = [ctypes.c_char_p, ctypes.c_char_p]

# Returned data should have the following format in JSON:
#    [ 
#        { 'id' : id,
#          'similarities' : [
#                {'id' : id, 'similarity' : { algo_id : percent, algo2_id : percent2}},
#               ...
#          ]
#       }
#   ]
@csrf_exempt
def compare(request):
    req = json.loads(request.POST['params'])
    lock = threading.RLock()
    result = []
    if 'files' not in req or 'algorithms' not in req:
        return HttpResponse(json.dumps({'error' : 'Wrong data: ' + str(req)}))
    
    files = req['files']
    for f in files:
        #get rid off spaces and make it a c-valid parameter
        f['code'] = ctypes.c_char_p(f['code']) 
    
    #n corridas, cada corrida tomamos el dato a mandar y creamos un arreglo sin el
    if len(files) < 2: 
        return HttpResponse(json.dumps({'error' : 'Just one file: ' + str(req)}))
    running_threads = []
    for i in range(len(files)):
        curr_file = files[i]
        to_compare = filter(lambda x: x['id'] != curr_file['id'], files)
        if 'code' not in curr_file:
            return HttpResponse(json.dumps({'error' : 'Wrong data: ' + str(req)}))
        similarities = []
        print "HOLA!"
        comparator = Comparator(curr_file, to_compare, result, lock, req)
        running_threads.append(comparator)
        comparator.start()
        #for tc in to_compare:
        #    curr_similarity = {'id' : tc['id'], 'similarity' : {}}
        #    if '1' in req['algorithms']:
        #        curr_similarity['similarity']['1'] = algorithms.ld_compare(curr_file['code'], tc['code'])
        #    if '2' in req['algorithms']:
        #        curr_similarity['similarity']['2'] = algorithms.sherlock_compare(curr_file['code'], tc['code'])
        #    similarities.append(curr_similarity)
        #result.append({'id' : curr_file['id'], 'similarities' : similarities})
    for t in running_threads:
        t.join()
    print json.dumps(result)
    return HttpResponse(json.dumps(result), mimetype='application/javascript')

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
                curr_similarity['similarity']['2'] = algorithms.sherlock_compare(curr_file['code'], tc['code'])
            similarities.append(curr_similarity)
        self.lock.acquire()
        result.append({'id' : curr_file['id'], 'similarities' : similarities})
        self.lock.release()
