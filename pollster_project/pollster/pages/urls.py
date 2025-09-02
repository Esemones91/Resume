from django.urls import path
from . import views

# define the routing of pages->index.html file
urlpatterns = [
    path('', views.index, name='index'),
]