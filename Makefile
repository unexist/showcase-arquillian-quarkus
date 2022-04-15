define JSON_TODO
curl -X 'POST' \
  'http://localhost:8080/todo' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "description": "string",
  "done": true,
  "dueDate": {
    "due": "2021-05-07",
    "start": "2021-05-07"
  },
  "title": "string"
}'
endef
export JSON_TODO

# Tools
rest-create:
	@echo $$JSON_TODO | bash

rest-list:
	@curl -X 'GET' 'http://localhost:8090/todo' -H 'accept: */*' | jq .

env:
	launchctl setenv TESTCONTAINERS_CHECKS_DISABLE true
	launchctl setenv TESTCONTAINERS_RYUK_DISABLED true
	launchctl setenv TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE unix://${HOME}/.local/share/containers/podman/machine/podman-machine-default/podman.sock