from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from .routes import main

app = FastAPI(
	title='Interenting',
	description='Decentralized Airbnb-like platform supporting rental investments',
)


origins = ['*']

app.add_middleware(
	CORSMiddleware,
	allow_origins=origins,
	allow_credentials=True,
	allow_methods=['*'],
	allow_headers=['*'],
)


app.include_router(main.router)
