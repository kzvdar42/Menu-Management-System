

def check_parameters_in_request(params, data):
    result = []
    for param in params:
        if param not in data:
            result.append(param)
    if len(result) == 0:
        return None
    return result
