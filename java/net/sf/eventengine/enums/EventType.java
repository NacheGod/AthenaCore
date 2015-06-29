/*
 * Copyright (C) 2015-2015 L2J EventEngine
 *
 * This file is part of L2J EventEngine.
 *
 * L2jAdmins is free software: you can redistribute it and/or modify
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
package net.sf.eventengine.enums;

/**
 * @author fissban
 */
public enum EventType
{
	AVA("All Vs All"),
	CTF("Capture The Flag"),
	TVT("Team Vs Team"),
	OVO("One Vs One"),
	SURVIVE("Survive");
	
	private final String _eventName;
	
	EventType(String eventName)
	{
		_eventName = eventName;
	}
	
	public String getEventName()
	{
		return _eventName;
	}
}
