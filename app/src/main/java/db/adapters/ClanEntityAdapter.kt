package db.adapters

import client.models.clan.Clan
import db.entities.ClanEntity

class ClanEntityAdapter {
    companion object {
        fun adapt(item: Clan): ClanEntity {
            return ClanEntity(
                name = item.name,
                tag = item.tag,
                locationName = item.location?.name,
                members = item.members,
                points = item.clanPoints,
                warsWon = item.warWins,
                warFrequency = item.warFrequency,
                type = item.type,
                requiredTrophies = item.requiredTrophies,
            )
        }
    }
}
