import simple_responses as s_resp
from checkers import check_parameters_in_request
import db_commands as db


def registration(data):
    result = check_parameters_in_request(["login", "password"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    login = data["login"]
    password = data["password"]
    if db.is_user_exists(login):
        return s_resp.error_response("User already exists")
    if "admin" in data:
        if not db.add_admin_user(login, password):
            return s_resp.error_response("Exception in DB")
    else:
        if not db.add_default_user(login, password):
            return s_resp.error_response("Exception in DB")
    if "phone_number" in data:
        db.add_phone_num_to_user(login, data["phone_number"])
    res = db.get_user_info(login)
    if res is not False:
        return s_resp.ok_response(parameters=res)
    else:
        return s_resp.error_response("User didn't found")


def login(data):
    result = check_parameters_in_request(["login", "password"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    login = data["login"]
    password = data["password"]
    if not db.is_user_exists(login):
        return s_resp.error_response("User doesn't exist")
    res = db.check_user(login, password)
    if not res:
        return s_resp.error_response("Error password")
    res = db.get_user_info(login)
    if res is not False:
        return s_resp.ok_response(parameters=res)
    else:
        return s_resp.error_response("User didn't found")


def confirm_admin(data):
    result = check_parameters_in_request(["login", "login_to_confirm"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    login = data['login']
    login_to_confirm = data['login_to_confirm']
    if not db.is_moderator(login):
        return s_resp.error_response("is not moderator")
    if db.confirm_user(login_to_confirm):
        return s_resp.ok_response()
    else:
        return s_resp.error_response("Error in DB")


def delete_account(data):
    pass