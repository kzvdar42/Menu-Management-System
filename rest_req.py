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
    result = check_parameters_in_request(["rest_id", "dish_name", "description", "price", "onoff"], data)
    if result is not None:
        return resp.value_not_exists(result)

    res, id = db.add_dish_fully(data["rest_id"],
                                data["dish_name"],
                                data["description"],
                                "",
                                data["price"],
                                data["onoff"])
    if res is False:
        return resp.error_response("500")
    return resp.ok_response(parameters={"dish_id": id})


def update_dish(data):
    result = check_parameters_in_request(["dish_id", "rest_id", "dish_name", "description", "price", "onoff"], data)
    if result is not None:
        return resp.value_not_exists(result)

    res, id = db.update_dish_fully(data["dish_id"],
                                   data["rest_id"],
                                   data["dish_name"],
                                   data["description"],
                                   data["price"],
                                   data["onoff"])
    if res is False:
        return resp.error_response("500")
    return resp.ok_response(parameters={"dish_id": id})


def set_dish_photo(data):
    result = check_parameters_in_request(["dish_id", "photo_src"], data)
    if result is not None:
        return resp.value_not_exists(result)
    if db.set_dish_photo_src(data["dish_id"], data["photo_src"]):
        return resp.ok_response()
    else:
        return resp.error_response()


def set_rest_photo(data):
    result = check_parameters_in_request(["rest_id", "photo_src"], data)
    if result is not None:
        return resp.value_not_exists(result)
    if db.set_rest_main_photo_src(data["rest_id"], data["photo_src"]):
        return resp.ok_response()
    else:
        return resp.error_response()


def set_dish_off(data):
    result = check_parameters_in_request(["dish_id"], data)
    if result is not None:
        return resp.value_not_exists(result)
    if db.switch_dish_off(data["dish_id"]):
        return resp.ok_response()
    else:
        return resp.error_response()


def set_dish_on(data):
    result = check_parameters_in_request(["dish_id"], data)
    if result is not None:
        return resp.value_not_exists(result)
    if db.switch_dish_on(data["dish_id"]):
        return resp.ok_response()
    else:
        return resp.error_response()


def delete_dish(data):
    result = check_parameters_in_request(["dish_id"], data)
    if result is not None:
        return resp.value_not_exists(result)
    if db.delete_dish(data["dish_id"]):
        return resp.ok_response()
    else:
        return resp.error_response()