/*
 * Copyright (C) 2015-2015 L2J EventEngine
 *
 * This file is part of L2J EventEngine.
 *
 * L2J EventEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L2J EventEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.eventengine.holder;

import java.util.Comparator;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.events.EventDispatcher;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerFameChanged;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerPvPChanged;

import net.sf.eventengine.enums.PlayerColorType;

/**
 * Clase encargada de administrar los datos de los players que participan del evento.
 * @author fissban
 */
public class PlayerHolder
{
	private final L2PcInstance _player;
	
	// Contador de kills realizados.
	private int _kills = 0;
	// Contador de deaths obtenidas.
	private int _deaths = 0;
	// Color original de un personaje por si en algun evento es cambiado.
	private int _oriColorTitle = Integer.decode("0xFFFFFF");
	// Titulo original de un personaje por si en algun evento es cambiado.
	private String _oriTitle = "";
	
	/** The number of player killed during a PvP (the player killed was PvP Flagged) */
	private int _pvpKills;
	/** The Fame of this PlayerHolder */
	private int _fame;
	
	private int _dinamicInstanceId = 0;
	
	public PlayerHolder(L2PcInstance player)
	{
		_player = player;
	}
	
	// METODOS VARIOS -----------------------------------------------------------
	
	/**
	 * Aacceso directo a todos los metodos de L2PcInstance.
	 * @return
	 */
	public L2PcInstance getPcInstance()
	{
		return _player;
	}
	
	public int getDinamicInstanceId()
	{
		return _dinamicInstanceId;
	}
	
	public void setDinamicInstanceId(int dinamicInstanceId)
	{
		_dinamicInstanceId = dinamicInstanceId;
	}
	
	/**
	 * Incrementamos en uno la cantidad de kills
	 */
	public void increaseKills()
	{
		_kills++;
	}
	
	public int getKills()
	{
		return _kills;
	}
	
	/**
	 * Incrementamos en uno la cantidad de muertes
	 */
	public void increaseDeaths()
	{
		_deaths++;
	}
	
	public int getDeaths()
	{
		return _deaths;
	}
	
	/**
	 * Obtenemos la cant de puntos de un player.<br>
	 * La formula se obtiene a partir de: (_kills-_deaths)<br>
	 * @return
	 */
	public int getPoints()
	{
		return _kills - _deaths;
	}
	
	/**
	 * @return the the PvP Kills of the PlayerHolder (Number of player killed during a PvP).
	 */
	public int getPvpKills()
	{
		return _pvpKills;
	}
	
	/**
	 * Set the the PvP Kills of the PlayerHolder (Number of player killed during a PvP).
	 * @param pvpKills
	 */
	public void setPvpKills(int pvpKills)
	{
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerPvPChanged(this.getPcInstance(), _pvpKills, pvpKills), this.getPcInstance());
		_pvpKills = pvpKills;
	}
	
	/**
	 * @return the Fame of this PlayerHolder
	 */
	public int getFame()
	{
		return _fame;
	}
	
	/**
	 * Set the Fame of this PlayerHolder
	 * @param fame
	 */
	public void setFame(int fame)
	{
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerFameChanged(this.getPcInstance(), _fame, fame), this.getPcInstance());
		_fame = (fame > Config.MAX_PERSONAL_FAME_POINTS) ? Config.MAX_PERSONAL_FAME_POINTS : fame;
	}
	
	public void setNewTitle(String title)
	{
		_oriTitle = _player.getTitle();
		_player.setTitle(title);
	}
	
	/**
	 * Recuperamos el titulo original de un player
	 */
	public void recoverOriginalTitle()
	{
		_player.setTitle(_oriTitle);
	}
	
	/**
	 * Disponemos un nuevo color del titulo para un player.
	 * @param colorHex
	 */
	public void setNewColorTitle(PlayerColorType colorHex)
	{
		_oriColorTitle = _player.getAppearance().getTitleColor();
		_player.getAppearance().setTitleColor(colorHex.getColor());
	}
	
	/**
	 * Recuperamos el color original del titulo del player
	 */
	public void recoverOriginalColorTitle()
	{
		_player.getAppearance().setTitleColor(_oriColorTitle);
	}
	
	// METODOS VARIOS --------------------------------------------------------------
	
	public static Comparator<PlayerHolder> _pointsComparator = new Comparator<PlayerHolder>()
	{
		@Override
		public int compare(PlayerHolder p1, PlayerHolder p2)
		{
			if (p1.getPoints() == p2.getPoints())
			{
				return p1.getPcInstance().getName().compareTo(p2.getPcInstance().getName());
			}
			else
			{
				return p1.getPoints() - p2.getPoints();
			}
		}
	};
}
