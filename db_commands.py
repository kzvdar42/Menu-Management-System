import sqlite3
import threading


def create_connection():
    try:
        conn = sqlite3.connect(os.path.join('.', "data.db"), check_same_thread=False)
        return conn
    except Exception as e:
        print(e)

    return None


conn = create_connection()

lock = threading.Lock()


def create_tables():
    c = conn.cursor()
    c.execute(""" CREATE TABLE IF NOT EXISTS Users (
                  user_id text,
                  user_lvl text
                  )""")
    c.close()


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
        print(e)
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
    finally:
        lock.release()


def some_command():
    return None
