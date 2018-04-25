import threading
import os
import pymysql
import time


def create_connection():
    try:
        conn = pymysql.connect("178.62.77.190", "usr", "12341234", "mms", use_unicode=True, charset='utf8')
        return conn
    except Exception as e:
        print("ERROR in create connection:", e)
    return None


conn = create_connection()

lock = threading.Lock()


def execute_command(command):
    global lock
    lock.acquire()
    try:
        c = conn.cursor()
        c.execute(command)
        data = c.fetchone()
        conn.commit()
        c.close()
        return data
    except Exception as e:
        print("ERROR in execute command:", e)
        raise e
    finally:
        lock.release()


def execute_command_fetchall(command):
    global lock
    lock.acquire()
    try:
        c = conn.cursor()
        c.execute(command)
        data = c.fetchall()
        conn.commit()
        c.close()
        return data
    except Exception as e:
        print(e)
        lock.release()
        raise e
    finally:
        lock.release()


def add_default_user(login, password):
    try:
        execute_command("INSERT INTO users(login, password, rights) "
                        "VALUES('{}', '{}', '{}');".format(login, password, 0))
    except Exception as e:
        print("ERROR in add_default_user:", e)
        return False
    return True


def add_phone_num_to_user(login, phone_num):
    try:
        execute_command("UPDATE users "
                        "SET phone_number = '{}' "
                        "WHERE login = '{}'".format(phone_num, login))
    except Exception as e:
        print("Error in db.add_phone_num_to_user,", e)
        return False
    return True


def add_admin_user(login, password):
    try:
        execute_command("INSERT INTO users(login, password, rights) "
                        "VALUES('{}', '{}', '{}');".format(login, password, 1))
    except Exception as e:
        print("ERROR in add_default_user:", e)
        return False
    return True


def add_moderator_user(login, password):
    try:
        execute_command("INSERT INTO users(login, password, rights) "
                        "VALUES('{}', '{}', '{}');".format(login, password, 2))
    except Exception as e:
        print("ERROR in add_default_user:", e)
        return False
    return True


def get_user_info(login):
    result = dict()
    try:
        res = execute_command("SELECT * "
                              "FROM users "
                              "WHERE login = '{}'".format(login))
        if len(res) > 1:
            result["login"] = res[0]
            result["configrmed"] = res[2]
            result["rights"] = res[3]
            result["rest"] = res[4]
            result["phone_num"] = res[5]
            return result
        else:
            return False
    except Exception as e:
        print("Exception in get_user_info", e)
        return False


def is_user_exists(login):
    try:
        res = execute_command("SElECT * "
                              "FROM users "
                              "WHERE login = '{}'".format(login))
        return res is not None and len(res) > 0
    except Exception as e:
        print("ERROR in is_user_exists:", e)
        return False


def check_user(login, password):
    try:
        res = execute_command("SELECT * "
                              "FROM users "
                              "WHERE login = '{}' AND password = '{}'".format(login, password))
        if len(res) > 0:
            result = {"confirmed": res[2], "rights": res[3], "rest": res[4], "phone_number": res[5]}
            return result
        else:
            return False
    except Exception as e:
        print("Error in check_user:", e)
        return False


def get_user_id(login):
    try:
        res = execute_command("SELECT id "
                              "FROM users "
                              "WHERE login = '{}'".format(login))
        if len(res) == 0:
            return None
        else:
            return res[0]
    except Exception as e:
        print("ERROR in get_user_id:", e)
        return False


def confirm_user(login):
    try:
        execute_command("UPDATE users "
                        "SET confirmed = TRUE "
                        "WHERE login = '{}' ".format(login))

    except Exception as e:
        print("ERROR in confirm user:", e)
        return False
    return True


def is_admin(login):
    try:
        res = execute_command("SELECT id "
                              "FROM users "
                              "WHERE login = {} AND rights = 1".format(login))
        if res is not None and res[0] == id:
            return True
    except Exception as e:
        print("ERROR in is_admin:", e)
        return False
    return False


def is_moderator(login):
    try:
        res = execute_command("SELECT login "
                              "FROM users "
                              "WHERE login = {} AND rights = 2".format(login))
        if res is not None and res[0] == login:
            return True
    except Exception as e:
        print("ERROR in is_moderator:", e)
        return False
    return False


def is_verified(login):
    try:
        res = execute_command("SELECT login "
                              "FROM users "
                              "WHERE login = {} AND rights = 1 AND confirmed = 1".format(login))
        if res is not None and res[0] == login:
            return True
    except Exception as e:
        print("ERROR in is_admin:", e)
        return False
    return False


def rest_list():
    result = []
    try:
        res = execute_command_fetchall("SELECT id, owner_id, rest_name, sub_name, description, phone_num, location, web_site, open_time, close_time, main_photo_src "
                              "FROM rest")
        for rest in res:
            result.append({'id': rest[0],
                           "owner_id": rest[1],
                           "rest_name": rest[2],
                           "sub_name": rest[3],
                           "description": rest[4],
                           "phone_num": rest[5],
                           "location": rest[6],
                           "web_site": rest[7],
                           "open_time": rest[8],
                           "close_time": rest[9],
                           "main_photo_src": rest[10]})
    except Exception as e:
        print("Exception in rest_list", e)
        return None
    return result


def add_rest(owner_id):
    result = None
    try:
        execute_command("INSERT INTO rest(owner_id) "
                        "VALUES('{}')".format(owner_id))
        res = execute_command("SELECT id "
                              "FROM rest "
                              "WHERE owner_id = {}".format(owner_id))
        if res is not None:
            result = res[0]
    except Exception as e:
        print("ERROR in add_rest", e)
        return False, None
    return True, result


def is_rest_owner(rest_id, owner_id):
    try:
        res = execute_command("SELECT owner_id "
                              "FROM rest "
                              "WHERE id = {}".format(rest_id))

        check_owner_id = res[0]
    except Exception as e:
        print("ERROR in is_rest_owner:", e)
        return False
    return owner_id == check_owner_id


def set_rest_name(rest_id, rest_name):
    try:
        execute_command("UPDATE rest "
                        "SET rest_name = '{}' "
                        "WHERE id = {} ".format(rest_name, rest_id))
    except Exception as e:
        print("ERROR in set_rest_name:", e)
        return False
    return True


def set_rest_subname(rest_id, sub_name):
    try:
        execute_command("UPDATE rest "
                        "SET sub_name = '{}' "
                        "WHERE id = {}".format(sub_name, rest_id))
    except Exception as e:
        print("ERROR in set_rest_subname:", e)
        return False
    return True


def set_rest_description(rest_id, description):
    try:
        execute_command("UPDATE rest "
                        "SET description = '{}' "
                        "WHERE id = {}".format(description, rest_id))
    except Exception as e:
        print("ERROR in set_rest_description:", e)
        return False
    return True


def set_rest_phone_num(rest_id, phone_num):
    try:
        execute_command("UPDATE rest "
                        "SET phone_num = '{}' "
                        "WHERE id = {}".format(phone_num, rest_id))
    except Exception as e:
        print("ERROR in set_rest_phone_num:", e)
        return False
    return True


def set_rest_location(rest_id, location):
    try:
        execute_command("UPDATE rest "
                        "SET location = '{}' "
                        "WHERE id = {}".format(location, rest_id))
    except Exception as e:
        print("ERROR in set_rest_location:", e)
        return False
    return True


def set_rest_web_site(rest_id, web_site):
    try:
        execute_command("UPDATE rest "
                        "SET web_site = '{}' "
                        "WHERE id = {}".format(web_site, rest_id))
    except Exception as e:
        print("ERROR in set_rest_web_site:", e)
        return False
    return True


def set_rest_open_time(rest_id, open_time):
    try:
        execute_command("UPDATE rest "
                        "SET open_time = '{}' "
                        "WHERE id = {}".format(open_time, rest_id))
    except Exception as e:
        print("ERROR in set_rest_open_time:", e)
        return False
    return True


def set_rest_close_time(rest_id, close_time):
    try:
        execute_command("UPDATE rest "
                        "SET close_time = '{}' "
                        "WHERE id = {}".format(close_time, rest_id))
    except Exception as e:
        print("ERROR in set_rest_close_time:", e)
        return False
    return True


def set_rest_main_photo_src(rest_id, main_photo_src):
    try:
        execute_command("UPDATE rest "
                        "SET main_photo_src = '{}' "
                        "WHERE id = {}".format(main_photo_src, rest_id))
    except Exception as e:
        print("ERROR in set_rest_main_photo_src:", e)
        return False
    return True


def add_photo(owner_id, src):
    try:
        execute_command("INSERT INTO photos(owner_id, src) "
                        "VALUES({}, '{}')".format(owner_id, src))

    except Exception as e:
        print("ERROR in add_photo:", e)
        return False
    return True


def add_photo_to_rest(rest_id, src):
    try:
        execute_command("UPDATE photos "
                        "SET rest_id = {} "
                        "WHERE src = '{}'".format(rest_id, src))
    except Exception as e:
        print("ERROR in add_photo_to_rest:", e)
        return False
    return True


def remove_photo_from_rest(src):
    try:
        execute_command("UPDATE photos "
                        "SET rest_id = NULL "
                        "WHERE src = '{}'".format(src))
    except Exception as e:
        print("ERROR in remove_photo_from_rest:", e)
        return False
    return True


def add_dish_fully(rest_id, name, description, photo_src, price, onoff):
    try:
        execute_command("INSERT INTO dish(rest_id, dish_name, description, photo_src, price, onoff) "
                        "VALUES({}, '{}', '{}', '{}', {}, {})".format(rest_id,
                                                                         name,
                                                                         description,
                                                                         photo_src,
                                                                         price,
                                                                         onoff))

        res = execute_command("SELECT (id) "
                              "FROM dish "
                              "WHERE rest_id = {} AND dish_name = '{}' AND price = {}".format(rest_id,
                                                                                                name,
                                                                                                price))
        if res is not None:
            return True, res[0]
        else:
            return False
    except Exception as e:
        print("ERROR in add_dish_fully:", e)
        return False


def add_dish(rest_id):
    try:
        execute_command("INSERT INTO dish(rest_id) "
                        "VALUES({})".format(rest_id))
        res = execute_command("SELECT MAX(id) "
                              "FROM dish "
                              "WHERE rest_id = {}".format(rest_id))
        if res is not None:
            return True, res[0]
        else:
            return False
    except Exception as e:
        print("ERROR in add_dish:", e)
        return False


def is_dish_owner(rest_id, dish_id):
    try:
        res = execute_command("SELECT rest_id "
                              "FROM dish "
                              "WHERE id = {}".format(dish_id))
        if res is None or res[0] != rest_id:
            return False
        else:
            return True
    except Exception as e:
        print("ERROR in is_dish_owner:", e)
    return False


def set_dish_name(dish_id, name):
    try:
        execute_command("UPDATE dish "
                        "SET dish_name = '{}' "
                        "WHERE id = {}".format(name, dish_id))
    except Exception as e:
        print("ERROR in set_dish_name:", e)
        return False
    return True


def set_dish_description(dish_id, description):
    try:
        execute_command("UPDATE dish "
                        "SET description = '{}' "
                        "WHERE id = {}".format(description, dish_id))
    except Exception as e:
        print("ERROR in set_dish_description:", e)
        return False
    return True


def set_dish_price(dish_id, price):
    try:
        execute_command("UPDATE dish "
                        "SET price = {} "
                        "WHERE id = {}".format(price, dish_id))
    except Exception as e:
        print("ERROR in set_dish_price:", e)
        return False
    return True


def set_dish_photo_src(dish_id, photo_src):
    try:
        execute_command("UPDATE dish "
                        "SET photo_src = '{}' "
                        "WHERE id = {}".format(photo_src, dish_id))
    except Exception as e:
        print("ERROR in set_dish_photo_src:", e)
        return False
    return True


def switch_dish_on(dish_id):
    try:
        execute_command("UPDATE dish "
                        "SET onoff = TRUE "
                        "WHERE id = {}".format(dish_id))
    except Exception as e:
        print("ERROR in set_dish_on:", e)
        return False
    return True


def switch_dish_off(dish_id):
    try:
        execute_command("UPDATE dish "
                        "SET onoff = FALSE "
                        "WHERE id = {}".format(dish_id))
    except Exception as e:
        print("ERROR in set_dish_off:", e)
        return False
    return True


def is_dish_on(dish_id):
    try:
        res = execute_command("SELECT onoff "
                              "FROM dish "
                              "WHERE id = {}".format(dish_id))
        if res is not None:
            return res[0] == 1
        else:
            return False
    except Exception as e:
        print("ERROR in is_sidh_on:", e)
    return False


def get_rest_menu(rest_id):
    result = []
    try:
        res = execute_command_fetchall("SELECT id, dish_name, description, photo_src, price "
                                       "FROM dish "
                                       "WHERE rest_id = {} AND onoff = 1".format(rest_id))
        for dish in res:
            result.append({"id": dish[0],
                           "rest_id": rest_id,
                           "dish_name": dish[1],
                           "description": dish[2],
                           "photo_src": dish[3],
                           "price": dish[4]})
    except Exception as e:
        print("Exception in get_rest_menu", e)
        return None
    return result


def get_rest_templates(rest_id):
    result = []
    try:
        res = execute_command_fetchall("SELECT id, dish_name, description, photo_src, price "
                                       "FROM dish "
                                       "WHERE rest_id = {} ".format(rest_id))
        for dish in res:
            result.append({"id": dish[0],
                           "dish_name": dish[1],
                           "description": dish[2],
                           "photo_src": dish[3],
                           "price": dish[4]})
    except Exception as e:
        print("Exception in get_rest_menu", e)
        return None
    return result
