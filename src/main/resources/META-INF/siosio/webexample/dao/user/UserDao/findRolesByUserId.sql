select /*%expand*/*
from user_roles
where user_id = /*userId*/'test_user'
