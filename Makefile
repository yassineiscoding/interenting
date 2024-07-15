install:
	poetry install --no-root

run:
	fastapi dev .\src\server.py --port 7000
