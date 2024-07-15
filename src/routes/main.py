from typing import Annotated
from fastapi import APIRouter, Depends

from ..controllers import MainController

router = APIRouter(prefix='/main', tags=['main'])


@router.get('/')
async def read_root(
	controller: Annotated[MainController, Depends(MainController)],
):
	return await controller.index()
