package db.adapters

import client.models.player.Player
import db.entities.PlayerEntity

class PlayerEntityAdapter {
    companion object {
        fun adapt(item: Player): PlayerEntity {
            return PlayerEntity(
                name = item.name,
                tag = item.tag,
                townHallLevel = item.townHallLevel,
                trophies = item.trophies,
                bestTrophies = item.bestTrophies,
                expLevel = item.expLevel,
                clan = item.clan?.name,
                role = item.role,
                warStars = item.warStars
            )
        }
    }
}
