package xianxian.btwasher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.google.common.collect.Sets;
import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.bcodec.BEValue;
import com.turn.ttorrent.bcodec.BEncoder;
import com.turn.ttorrent.bcodec.InvalidBEncodingException;
import com.turn.ttorrent.common.Torrent;

import xianxian.btwasher.utils.FileUtils;
import xianxian.btwasher.utils.TorrentUtils;

public class BTWasher {
	/**
	 * 
	 * @param args[0] is file name
	 */
	public static void main(String[] args) {
		try {
			BEValue beValue = BDecoder.bdecode(new FileInputStream(args[0]));
			Iterator<Entry<String, BEValue>> iterator = beValue.getMap().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, BEValue> entry = (Entry<String, BEValue>) iterator.next();
				if (entry.getKey().equals("info")) {
					// BEValue value = ;
					entry.getValue().getMap().entrySet().forEach((t) -> {
						Entry<String, BEValue> e = t;
						String key = e.getKey();
						if (key.equals("name") || key.equals("name.utf-8")) {
							try {
								System.out.println(e.getValue().getString());
								e.setValue(new BEValue("Washed"));
							} catch (UnsupportedEncodingException | InvalidBEncodingException e1) {
								e1.printStackTrace();
							}

						} else if (key.equals("files")) {
							try {
								Container<Integer> con = new Container<Integer>(new Integer(0));
								System.out.println(e.getValue().getList());
								e.getValue().getList().forEach((a) -> {
									//a.
									
									try {
										Set<Entry<String, BEValue>> entrySet = a.getMap().entrySet();

										entrySet.forEach((c) -> {
											Entry<String, BEValue> cEntry = c;
											String cKey = cEntry.getKey();
											if (cKey.equals("path")) {

												try {
													int count = con.getElement().intValue();
													List<BEValue> list = cEntry.getValue().getList();
													String lastFile = list.get(list.size() - 1).getString();
													if (lastFile.contains("")) {
														String extension = FileUtils.getFileExtension(lastFile);
														if (lastFile.contains("padding_file")) {
															return;
														} else {
															list.clear();
															String newFile = count + "-Washed" + extension;
															System.out.println(newFile);
															list.add(new BEValue(newFile));
															con.setElement(new Integer(count+1));;
														}

													}
												} catch (InvalidBEncodingException | UnsupportedEncodingException e1) {
													// TODO 自动生成的 catch 块
													e1.printStackTrace();
												}

											} else if (cKey.equals("path.utf-8")) {
												entrySet.forEach((d)->{
													Entry<String, BEValue> dEntry = d;
													if (dEntry.getKey().equals("path")) {
														try {
															List<BEValue> list = dEntry.getValue().getList();
															cEntry.setValue(new BEValue(list));
														} catch (InvalidBEncodingException e1) {
															// TODO 自动生成的 catch 块
															e1.printStackTrace();
														}

													}
												});;
												
												
											}
										});
//										.forEach((b) -> {
//											String bKey = b.getKey();
//											System.out.println(bKey);
//										});
										
									} catch (InvalidBEncodingException e1) {
										// TODO 自动生成的 catch 块
										e1.printStackTrace();
									}
								});
							} catch (InvalidBEncodingException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
						}
					});

				}
				System.out.println(entry.getKey());
			}
			BEncoder.bencode(beValue.getMap(), new FileOutputStream(FileUtils.getFilePathWithoutExtension(args[0]) + "-Washed" + FileUtils.getFileExtension(args[0])));
			// Torrent torrent = TorrentUtils.load("G:\\ppi.torrent", false);
			// List<String> files = torrent.getFilenames();
			// for (int i = 0; i < files.size(); i++) {
			// String string = files.get(i);
			// System.out.println(string);
			// System.out.println(FileUtils.getFileExtension(string));
			// if (!string.contains("_____padding_file_")) {
			// String extension = FileUtils.getFileExtension(string);
			// if (!extension.equals(".bc!")) {
			// String target = i + extension;
			// files.remove(i);
			// files.add(i, target);
			// }
			// }
			// }
			// FileUtils.writeByteArrayToFile(torrent.getEncoded(), "G:\\test.torrent");
		} catch (/* NoSuchAlgorithmException | */IOException e) {

			e.printStackTrace();
		}
	}
	public static class Container<E> {
		private E element;
		
		public Container(E element){
			this.element = element;
		}
		public void setElement(E element) {
			this.element = element;
		}
		public E getElement() {
			return element;
		}
	}
}
