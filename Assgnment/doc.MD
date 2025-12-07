## SQLServer management
```
Phân quyền database
  Security -> Logins -> (demo -> Properties)
    -> User Mapping 
      (-> AssignmentDB (Default Schema: dbo) 
      (-> Database role membership for: AssignmentDB -> db_owner))
    -> Ok

Export database:
  database → Tasks → Generate Scripts…
    Types of data to script | Schema and Data
    Script Indexes	        | True
    Script Triggers	        | True
    Script Foreign Keys	    | True
    Script Primary Keys	    | True
```
