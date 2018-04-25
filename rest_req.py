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


def rest_templates(data):
    result = check_parameters_in_request(["id"], data)
    if result is not None:
        return resp.value_not_exists(result)
    res = db.get_rest_templates(data["id"])
    result = {"result": "OK"}
    if res is not None:
        result["rest_menu"] = res
        return json.dumps(result)
    else:
        return resp.error_response()


def add_dish(data):
    result = check_parameters_in_request(["rest_id", "dish_name", "description", "photo_src", "price", "onoff"])
    if result is not None:
        return resp.value_not_exists(result)
    res, id = db.add_dish_fully(data["rest_id"],
                           data["dish_name"],
                           data["description"],
                           data["photo_src"],
                           data["prce"],
                           data["onoff"])
    if res is False:
        return resp.error_response("500")
    return resp.ok_response(parameters={"dish_id": id})
