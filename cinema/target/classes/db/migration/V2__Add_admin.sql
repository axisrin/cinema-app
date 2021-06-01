insert into usr (id, username, password, active)
    values (1, 'admin', '$2y$08$f8.My6PVg/rWujjl63lggO7VB822AAexTsmKPlxzCx6DSyw0B3LFC', '1');

insert into user_role(user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');