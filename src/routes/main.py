from typing import Union
from fastapi import APIRouter

main_router = APIRouter(prefix="/main", tags=["main"])


@main_router.get("/")
def read_root():
    return {"Hello": "World"}


@main_router.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}
