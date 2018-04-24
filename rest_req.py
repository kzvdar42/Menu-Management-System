import db_commands as db
import simple_responses as resp
import json


def rest_list():
    res = db.rest_list()
    result = {"result": "OK"}
    if res is not None:
        result["rest_list"] = res
        return json.dumps(result)
    else:
        return resp.error_response()


def rest_menu(data):
    res = db.get_rest_menu(data["id"])
    result = {"result": "OK"}
    if res is not None:
        result["rest_menu"] = res
        return json.dumps(result)
    else:
        return resp.error_response()
