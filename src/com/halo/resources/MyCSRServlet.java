package com.halo.resources;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


@Path("/myCSR")
public class MyCSRServlet {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/gamertag")
	public String getGamertagInfo(@DefaultValue("Enter gamertag") @QueryParam("gamertag") String gamertag) {
		ArrayList<String> returnList = new ArrayList<String>();
		PlayerProfile pp = new PlayerProfile();
		ParseHelper helper = new ParseHelper();

		String playlist = "";
		String rank = "";
		Document doc = null;
		try {
			doc = Jsoup.connect("https://stats.svc.halowaypoint.com/en-US/players/" + gamertag + "/h4/servicerecord?_=1383093738953").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc = Jsoup.parse(doc.toString(), "", Parser.xmlParser());

		for (Element e: doc.select("SkillRank")) {

			Elements playListName = e.select("playlistname");
			for (Element playList: playListName) {
				playlist = playList.toString();
			}

			Elements rankInPlayList = e.select("currentskillrank");
			for (Element rankIncoming: rankInPlayList) {
				rank = rankIncoming.toString();
			}

			playlist = helper.parsePlayList(playlist);
			rank = helper.parseRank(rank);

			pp.addPlayList(playlist);
			pp.addRank(rank);
		}

		returnList = helper.combineLists(pp, pp.getPlayList(),
				pp.getRankList());
		return returnList.toString();
	}
}
