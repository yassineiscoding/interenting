from fastapi import FastAPI

from src.routes import main_router

app = FastAPI(
    title="Interenting",
    description="Decentralized Airbnb-like platform supporting rental investments",
)


app.include_router(main_router)
