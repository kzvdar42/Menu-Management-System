from flask import Flask, request, Response, send_from_directory
from flask_cors import CORS
import os
import json
from verification import registration, login, delete_account, confirm_admin

from rest_req import rest_list, rest_menu, rest_templates
import rest_req
import db_commands as db
import simple_responses as s_resp
import random
app = Flask(__name__)
CORS(app)


@app.route('/<path:filename>', methods=['GET'])
def get_file(filename):
    if os.path.exists(os.path.join(".", "data", filename)):
        return send_from_directory(os.path.join(".", "data"), filename)
    else:
        return Response(response=json.dumps({"result": 404, "status": 200}), status=404)


@app.route('/api/is_up', methods=['GET', 'POST'])
def is_available():
    return Response(response=json.dumps({"result": True, "status": "200"}), status=200)


@app.route('/api', methods=['POST'])
def api_request():

    try:
        data = json.loads(request.data)
    except:
        return s_resp.error_response(info="json is not valid")
    print("REQUEST =", request)
    print("data = ", data)
    if "type" not in data:
        return s_resp.error_response("type is empty")
    if data["type"] == "registration":
        return registration(data)
    elif data["type"] == "login":
        return login(data)
    elif data["type"] == "rest_list":
        return rest_list()
    elif data["type"] == "rest_menu":
        return rest_menu(data)
    elif data["type"] == "rest_templates":
        return rest_templates(data)
    elif data["type"] == "delete_acc":
        return delete_account(data)
    elif data["type"] == "confirm_admin":
        return confirm_admin(data)
    elif data["type"] == "add_dish":
        return rest_req.add_dish(data)
    elif data["type"] == "set_dish_photo":
        return rest_req.set_dish_photo(data)
    elif data["type"] == "set_dish_on":
        return rest_req.set_dish_on(data)
    elif data["type"] == "set_dish_off":
        return rest_req.set_dish_off(data)
    else:
        return s_resp.error_response("Unknown")


@app.route('/api/upload_photo', methods=['POST'])
def upload_photo():
    photo = request.files["photo"]
    user_id = request.form["user_id"]
    name = get_new_name()
    photo.save(os.path.join(".", "data", name))
    db.add_photo(user_id, name)
    return s_resp.ok_response()


def get_new_name():
    return "%032x" % random.getrandbits(128)


def pre_check():
    if not os.path.exists(os.path.join(".", "data")):
        os.makedirs(os.path.join(".", "data"))

if __name__ == "__main__":
    pre_check()
    app.run(host='0.0.0.0', port=5303, debug=False)

