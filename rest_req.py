import db_commands as db
import simple_responses as resp


def rest_list():
    res = db.rest_list()
    result = {"result": "OK"}
    if res is not None:
        result["rest_list"] = res
        return result
    else:
        return resp.error_response()


def rest_menu(data):
    res = db.get_rest_menu(data["id"])
    result = {"result": "OK"}
    if res is not None:
        result["rest_menu"] = res
        return result
    else:
        return resp.error_response()
