package com.github.henmja.bachelor.pacbattle.network;

import com.github.henmja.bachelor.pacbattle.game.Pacbattle;

import org.glassfish.tyrus.client.ClientManager;

import javax.json.*;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final Pacbattle game;

    private Set<Player> players;
    
    private boolean twoP;
    private boolean threeP;
    private boolean fourP;


	private Session backend;
    
    private int x, y;
    
    public GameSession(Pacbattle game) throws DeploymentException {
        this.game = game;
        players = new HashSet<>();
    }

    public void connect() throws URISyntaxException, IOException, DeploymentException {
        ClientManager client = ClientManager.createClient();
        client.connectToServer(WebsocketClient.class, new URI("ws://localhost:3001/ws"));
    }

    public void onOpen(Session session) throws IOException, EncodeException {
        backend = session;
        sendToBackend("identify", "game");
    }

    private void sendToBackend(String action, String data) throws IOException, EncodeException {
        backend.getBasicRemote().sendObject(Json.createObjectBuilder()
                .add("action", action)
                .add("data", data)
                .build());
    }

    public void onMessage(Session session, String message) throws IOException, EncodeException {
        JsonReader jsonReader = Json.createReader(new StringReader(message));
        JsonObject jsonObj = jsonReader.readObject();
        jsonReader.close();

        if (!(jsonObj.containsKey("action") && jsonObj.containsKey("data"))) {
            return;
        }

        String action = jsonObj.getString("action");
        switch (action) {
        case "move": {
        	
            String data = jsonObj.getJsonNumber("data").toString();

            int position;

            try {
                position = Integer.parseInt(data);
            } catch (Exception e) {
                position = -1;
            }

            if (!jsonObj.containsKey("from")) {
                return;
            }

            String from = jsonObj.getString("from");
            
            int i = 0;
            for (Player player : players) {
            	if (i==4) {
            		i = 0;
            	}
                if (player.getId().equals(from) && i == 0) {
                	game.moveP1(position);
                } else if (player.getId().equals(from) && i == 1) {
                	game.moveP2(position);
                } else if (player.getId().equals(from) && i == 2) {
                	game.moveP3(position);
                } else if (player.getId().equals(from) && i == 3) {
                	game.moveP4(position);
                }
                i++;
            }
        }
    }
        switch (action) {
    case "moveLeft": {
    	
    	
        String data = jsonObj.getJsonNumber("data").toString();

        int position;

        try {
            position = Integer.parseInt(data);
        } catch (Exception e) {
            position = -1;
        }

        if (!jsonObj.containsKey("from")) {
            return;
        }
        
        
        String from = jsonObj.getString("from");
        int i = 0;
        for (Player player : players) {
        	if (i==4) {
        		i = 0;
        	}
            if (player.getId().equals(from) && i == 0) {
            	game.moveP1Left(position);
            } else if (player.getId().equals(from) && i == 1) {
            	game.moveP2Left(position);
            } else if (player.getId().equals(from) && i == 2) {
            	game.moveP3Left(position);
            } else if (player.getId().equals(from) && i == 3) {
            	game.moveP4Left(position);
            }
            i++;
        }
    }
}
        
        
        switch (action) {
        case "moveUp": {
        	
        	
            String data = jsonObj.getJsonNumber("data").toString();

            int position;

            try {
                position = Integer.parseInt(data);
            } catch (Exception e) {
                position = -1;
            }

            if (!jsonObj.containsKey("from")) {
                return;
            }
            
            
            String from = jsonObj.getString("from");
            int i = 0;
            for (Player player : players) {
            	if (i==4) {
            		i = 0;
            	}
                if (player.getId().equals(from) && i == 0) {
                	game.moveP1Up(position);
                } else if (player.getId().equals(from) && i == 1) {
                	game.moveP2Up(position);
                } else if (player.getId().equals(from) && i == 2) {
                	game.moveP3Up(position);
                } else if (player.getId().equals(from) && i == 3) {
                	game.moveP4Up(position);
                }
                i++;
            }
        }
        }
        
        switch (action) {
            case "identify": {
                String data = jsonObj.getString("data");

                if (data.equals("ok")) {
                    backend.getBasicRemote().sendObject(Json.createObjectBuilder()
                            .add("action", "get clients")
                            .build());
                }

                break;
            }
            case "get clients": {
                if (jsonObj.isNull("data")) {
                    break;
                }
                if (players.size()<=4) {
                JsonArray clients = jsonObj.getJsonArray("data");
                
                for (JsonValue client : clients) {
                	
                    String id = client.toString();
                    //0 til 3 fordi adder element til set etter at man sjekker setet for størrelse
                    if (players.size()==0) { 
                    	x = 80;
                    	y = 360;
                    
                    } else if (players.size()==1) {
                    	x=640;
                    	y=80;
                    	setTwoP(true);
                    
                    } else if (players.size()==2) {
                    	x=1200;
                    	y=360;
                    	setThreeP(true);
                    	
                    } else if (players.size()==3) {
                    	x=640;
                    	y=640;
                    	setFourP(true);
                    	
                    }
                    System.out.println(id);
                    players.add(new Player(id, x, y));
                    
               
                    
                    sendToBackend("get username", id);
                }
                }
                break;
            }
            case "added client": {
            	if (players.size()<=4) {
                String id = jsonObj.getString("data");
                
                if (players.size()==0) {
                	
                	x = 50;
                	y = 350;
                } else if (players.size()==1) {
                	
                	x=100;
                	y=200;
                	setTwoP(true);
                } else if (players.size()==2) {
                	
                	x=100;
                	y=100;
                	setThreeP(true);
                } else if (players.size()==3) {
                	x=200;
                	y=250;
                	setFourP(true);
                }
                System.out.println(id);
                players.add(new Player(id, x, y));
        
                
                sendToBackend("get username", id);
                }

                break;
            }
            case "dropped client": {
                String id = jsonObj.getString("data");

                for (Player player : players) {
                    if (player.getId().equals(id)) {
                        players.remove(player);
                        break;
                    }
                }
                break;
            }
            case "get username": {
                JsonArray client = jsonObj.getJsonArray("data");

                String id = client.get(0).toString();
                String username = client.get(1).toString();

                for (Player player : players) {
                    if (player.getId().equals(id)) {
                        player.setUsername(username);

                        break;
                    }
                }

                break;
            }
            
            
            case "moveRight": {
            	
                String data = jsonObj.getJsonNumber("data").toString();

                int position;

                try {
                    position = Integer.parseInt(data);
                } catch (Exception e) {
                    position = -1;
                }

                if (!jsonObj.containsKey("from")) {
                    return;
                }
                
                String from = jsonObj.getString("from");
                int i = 0;
                for (Player player : players) {
                	if (i==4) {
                		i = 0;
                	}
                    if (player.getId().equals(from) && i == 0) {
                    	game.moveP1Right(position);
                    } else if (player.getId().equals(from) && i == 1) {
                    	game.moveP2Right(position);
                    } else if (player.getId().equals(from) && i == 2) {
                    	game.moveP3Right(position);
                    } else if (player.getId().equals(from) && i == 3) {
                    	game.moveP4Right(position);
                    }
                    i++;
                }
            }
            
        }
        
    }

    public Set<Player> getPlayersSet() {
		return players;
	}

	public void setPlayersSet(Set<Player> playersSet) {
		this.players = playersSet;
	}

    public boolean isTwoP() {
		return twoP;
	}

	public void setTwoP(boolean twoP) {
		this.twoP = twoP;
	}

	public boolean isThreeP() {
		return threeP;
	}

	public void setThreeP(boolean threeP) {
		this.threeP = threeP;
	}

	public boolean isFourP() {
		return fourP;
	}

	public void setFourP(boolean fourP) {
		this.fourP = fourP;
	}

	public class Player {
        private String id;
        private String username;
        private int score = 0;
        private int x;
        private int y;

        private Player(String id, int x, int y) {
            this.id = id;
            this.setX(x);
            this.setY(y);
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public void increaseScore() {
            score++;
        }

        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            return ((Player) o).getId().equals(getId());
        }

        @Override
        public int hashCode() {
            return getId().hashCode();
        }

        public void setUsername(String username) {
            this.username = username;
        }

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
    }
}

