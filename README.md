# MysqlPlayerData

You have a Minecraft server and you want to syncronize PlayerDatas between all of your servers ? You are in the right place !

## Features :

* Syncronize PlayerDatas like :
    * Inventory
    * Armor
    * EnderChest
    * Second Hand
    * Food
    * Saturation
    * Experience
    * Health
* Use Mysql or MariaDB
* Auto Save (Default as false)

## Config

The plugin have a really simple config :

```yaml
# Database config part
ddb:
  # Database host
  host: localhost
  # Database user password
  pass: root
  # Database user username
  user: root
  # Database name
  name: inventories
  # Database host port
  port: 3306
  # Table name
  tablename: inventories
# Autosync config part
autosync:
  # true if you want autosync
  activated: false
  # Time before the first autosync (In seconds)
  first: 60
  # Time between autosync (In seconds)
  between: 60
```

## Commands

`/msave <Player>` : Save player inventory, Player is optionnal

`/mload <Player>` : Load player inventory, Player is optionnal

`/mreload` : Reload plugin config, except autosync config

## Permissions

`minv.*` : Allow all commands

`minv.save` : Allow msave command

`minv.load` : Allow mload command

`minv.reload` : Allow mreload command

## Support

You have a problem ? Feel free to open an issue [here](https://github.com/Vortezz/mysqlplayerdata/issues)<br>
You can also join my Discord [here](https://vortezz.dev/discord)
