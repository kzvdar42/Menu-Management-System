import simple_responses as s_resp
from checkers import check_parameters_in_request
import db_commands as db


def registration(data):
    result = check_parameters_in_request(["phone_number", "password"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    login = data["phone_number"]
    password = data["password"]
    if db.is_user_exists(login):
        return s_resp.error_response("User already exists")
    if "admin" in data:
        if not db.add_admin_user(login, password):
            return s_resp.error_response("Exception in DB")
    else:
        if not db.add_default_user(login, password):
            return s_resp.error_response("Exception in DB")
    user_id = db.get_user_id(login)
    if user_id is None or user_id is False:
        return s_resp.error_response("id is not found")
    else:
        return s_resp.ok_response(parameters={"id": user_id})


def login(data):
    result = check_parameters_in_request(["phone_number", "password"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    login = data["phone_number"]
    password = data["password"]
    if not db.is_user_exists(login):
        return s_resp.error_response("User doesn't exist")
    if not db.check_user(login, password):
        return s_resp.error_response("Error password")
    user_id = db.get_user_id(login)
    if user_id is None or user_id is False:
        return s_resp.error_response("id is not found")
    if db.is_admin(user_id):
        if not db.is_verified(user_id):
            return s_resp.error_response("not verified")
        else:
            return s_resp.ok_response(parameters={"id": user_id, "admin": True})
    return s_resp.ok_response(parameters={"id": user_id})


def confirm_admin(data):
    result = check_parameters_in_request(["id", "id_to_confirm"], data)
    if result is not None:
        return s_resp.value_not_exists(result)
    id = data['id']
    id_to_confirm = data['id_to_confirm']
    if not db.is_moderator(id):
        return s_resp.error_response("is not moderator")
    if db.confirm_user(id_to_confirm):
        return s_resp.ok_response()
    else:
        return s_resp.error_response("Error in DB")


def delete_account(data):
    pass