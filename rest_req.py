import db_commands as db
import simple_responses as resp
import json
from checkers import check_parameters_in_request

def rest_list():
    res = db.rest_list()
    result = {"result": "OK"}
    if res is not None:
        result["rest_list"] = res
        return json.dumps(result)
    else:
        return resp.error_response()


def rest_menu(data):
    result = check_parameters_in_request(["id"], data)
    if result is not None:
        return resp.value_not_exists(result)
    res = db.get_rest_menu(data["id"])
    result = {"result": "OK"}
    if res is not None:
        result["rest_menu"] = res
        return json.dumps(result)
    else:
        return resp.error_response()
