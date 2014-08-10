package com.halo.resources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ParseHelper {
	
	/**
	 * Empty default constructor
	 */
	public ParseHelper(){}
	
	/**
	 * Returns a list of the users playlists
	 * @param playlist
	 * @return
	 */
	public String parsePlayList(String playlist){
		return playlist.substring(14, playlist.length() - 15).trim();
	}
	
	/**
	 * Returns a list sthe users ranks
	 * @param rank
	 * @return
	 */
	public String parseRank(String rank){
		if(rank.trim().equals("<currentskillrank i:nil=\"true\" />")){
			return "0";
		}
		return rank.substring(18, rank.length() - 19);
	}
	
	/**
	 * Returns the players stats wrapped in the PlayerProfile object
	 * @param gamertag
	 * @return
	 * @throws IOException
	 */
	public PlayerProfile getPlayerStats(String gamertag) throws IOException{
		PlayerProfile pp = new PlayerProfile();
	
		String playlist = "";
		String rank = "";
		Document doc = Jsoup.connect("https://stats.svc.halowaypoint.com/en-US/players/"+ gamertag + "/h4/servicerecord?_=1383093738953").get();
		doc = Jsoup.parse(doc.toString(), "", Parser.xmlParser());
		
		for(Element e: doc.select("SkillRank")){
			
			Elements playListName = e.select("playlistname");
			for(Element playList: playListName){
				playlist = playList.toString();
			}
			
			Elements rankInPlayList = e.select("currentskillrank");
			for(Element rankIncoming: rankInPlayList){
				rank = rankIncoming.toString();
			}
			
			playlist = parsePlayList(playlist);
			rank = parseRank(rank);
			
			pp.addPlayList(playlist);
			pp.addRank(rank);
		}
		
		return pp;
	}
	
	/**
	 * Combines the two lists in the player profile
	 * @param pp
	 * @param list
	 * @param list2
	 * @return
	 */
	public ArrayList<String> combineLists(PlayerProfile pp, List<String> list, List<String> list2){
		ArrayList<String> combinedList = new ArrayList<String>();
		int playlistCounter = 0;
		int rankCounter = 0;
		for(int i = 0; i < pp.getPlayList().size() * 2; i++){
			if(i % 2 == 0){
				combinedList.add(pp.getPlayList().get(playlistCounter));
				playlistCounter++;
			}
			else{
				combinedList.add(pp.getRankList().get(rankCounter));
				rankCounter++;
			}
		}
		
		return combinedList;
	}

}
