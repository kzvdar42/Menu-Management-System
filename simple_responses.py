import json


def error_response(info=None, parameters=None):
    res = {"result": "error"}
    if info is not None:
        res["info"] = info
    if parameters is not None:
        for param in parameters:
            res[param] = parameters[param]
    return json.dumps(res)


def value_not_exists(info=None, parameters=None):
    res = {"result": "value not exists"}
    if info is not None:
        res["info"] = info
    if parameters is not None:
        for param in parameters:
            res[param] = parameters[param]
    return json.dumps(res)


def ok_response(info=None, parameters=None):
    res = {"result": "OK"}
    if info is not None:
        res["info"] = info
    if parameters is not None:
        for param in parameters:
            res[param] = parameters[param]
    return json.dumps(res)
