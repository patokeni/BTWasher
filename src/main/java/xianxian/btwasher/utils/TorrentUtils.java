package xianxian.btwasher.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.turn.ttorrent.common.Torrent;

public class TorrentUtils {

	public static Torrent load(String torrent, boolean seeder) throws NoSuchAlgorithmException, IOException {
		return new Torrent(FileUtils.readFileToByteArray(torrent), seeder);
	}

	public static Torrent load(File torrent, boolean seeder) throws NoSuchAlgorithmException, IOException {
		// Torrent
		return new Torrent(FileUtils.readFileToByteArray(torrent), seeder);
	}
}
