from flask import Flask, request, Response, send_from_directory
from flask_cors import CORS
import os


app = Flask(__name__)
CORS(app)


@app.route('/<path:filename>', methods=['GET'])
def get_file(filename):
    if os.path.exists(os.path.join(".", "data", filename)):
        return send_from_directory(os.path.join(".", "data"), filename)
    else:
        return Response(response={"result": 404, "status": 200}, status=404)


@app.route('/api/is_available')
def is_available():
    return Response(response={"result": True, "status": "200"}, status=200)


# @app.route('/api/')


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, threading=True, debug=False)
