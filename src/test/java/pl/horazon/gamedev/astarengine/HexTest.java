package pl.horazon.gamedev.astarengine;

import org.junit.jupiter.api.Test;

import pl.horazon.gamedev.astarengine.hex.GameBoardHex;

public class HexTest {

	@Test
	public void test() {
		GameBoardHex boardHex = new GameBoardHex("map-hex.txt");
		boardHex.loadMap();
	}
}
