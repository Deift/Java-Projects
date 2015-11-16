package Modelo;



import java.util.*;


public class TableroSolitario {
	
	//Representacion del tablero : 0 vacia, 1 ficha, - 1 no autorizado
	public int[][] tablero = new int[7][7];
	
	private int nFichas = 32;  //N�mero de fichas
	private int nJugadas = 0; 	//n�mero de jugadas
	
	/**Vector donde se guardan las vistas
	*/
	private Vector listadevistas = new Vector();
		
	public TableroSolitario() {//Este metodo inicializa el tablero
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				tablero[i][j] = -1;
			}
		}
		for (int i = 0; i < 2; i++) {
			for( int j = 2; j < 5; j++)  {
				tablero[i][j] = 1;
			}
		}
		for (int i = 2 ;i < 5; i++) {
			for( int j = 0; j < 7; j++)  {
				tablero[i][j] = 1;
			}
		}
		for (int i = 5; i < 7; i++) {
			for( int j = 2; j < 5; j++)  {
				tablero[i][j] = 1;
			}//for
		}//for
		tablero[3][3] = 0;
	}

	//------------------------------------------------------------
	//--- M�thodes -----------------------------------------------
	//------------------------------------------------------------
	public boolean jugada(int filaOrg,int colOrg,int filaDes, int colDes) {
		int filaMedia = (filaOrg+filaDes)/2;
		int colMedia = (colOrg + colDes)/2;
		if ((filaOrg == filaDes && Math.abs(colOrg - colDes) == 2)||(colOrg == colDes && Math.abs(filaOrg - filaDes) == 2)) {
			boolean b = tablero[filaMedia][colMedia] == 1 && tablero[filaDes][colDes] == 0;
			if (b) {
			tablero[filaOrg][colOrg] = 0;
			tablero[filaMedia][colMedia] = 0;
			tablero[filaDes][colDes] = 1;
			nJugadas++;
			nFichas--;////Este metodo recalcula los valores de las posiciones y llama a fijarcambio y este a cambio(metodo abstracto) implementado en ConsolaTablero para redibujar el tablero 
			fijarCambio();
			}
			return b;
		}
		else return false;
	}

	
	public int getnumFichaQuedan() { return nFichas;}//Devuelve el numero de fichas que quedan en el tablero
	
	public void reinicio() {//Inicializa el tablero para volver a empezar jugar y llama a fijarcambio que llama a cambio(metodo abstracto) implementado en ConsolaTablero para dibujarlo
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				tablero[i][j] = -1;
			}//for
		}//for
		for (int i = 0; i < 2; i++) {
			for( int j = 2; j < 5; j++)  {
				tablero[i][j] = 1;
			}//for
		}//for
		for (int i = 2 ;i < 5; i++) {
			for( int j = 0; j < 7; j++)  {
				tablero[i][j] = 1;
			}//for
		}//for
		for (int i = 5; i < 7; i++) {
			for( int j = 2; j < 5; j++)  {
				tablero[i][j] = 1;
			}//for
		}//for
		tablero[3][3] = 0;//case centrale vide
		nFichas = 32;
		nJugadas = 0;
		fijarCambio();
	}
	
	public boolean testPerder() {//Este metodo comprueba en cada movimiento si hemos perdido mirando una serie de condiciones
		boolean perder = true;
		boolean cond1,cond2,cond3,cond4;
		for (int i = 0;i<7;i++) {
			for (int j =0;j<7;j++) {
			  if (tablero[i][j] == 1) {
			  try {
				   cond1 = (tablero[i+1][j] == 1) && (tablero[i+2][j] == 0);
				}//try
				catch (IndexOutOfBoundsException iob) {cond1 = false;}
				
				try {
					cond2 = (tablero[i-1][j] == 1) && (tablero[i-2][j] == 0);
				}//try
				catch (IndexOutOfBoundsException iob) {cond2 = false;}	
				
				try {
					cond3 = (tablero[i][j+1] == 1) && (tablero[i][j+2] == 0);
				}//try
				catch (IndexOutOfBoundsException iob) {cond3 = false;}
				
				try {
					cond4 = (tablero[i][j-1] == 1) && (tablero[i][j-2] == 0);
				}//try
				catch (IndexOutOfBoundsException iob) {cond4 = false;}
				
				
				perder = perder && !(cond1 || cond2 || cond3 || cond4);
				}//if
			}//for
		}//for
		
		return perder;
	}
	

	public void addVista(VistaSolitario vs) {
		listadevistas.addElement(vs);//A�ade una vista al vector de vistas
	}
	public void retirarVista(VistaSolitario vs) {//Elimina una vista del vector de vistas
		listadevistas.removeElement(vs);
	}
	public void fijarCambio() {// este metodo fija el cambio realizado en el tablero,mirando todos los elemntos de la lista  y cambiando cada elemento con el metodo cambio.
		Enumeration e = listadevistas.elements();
		while(e.hasMoreElements()) {
			VistaSolitario vs = (VistaSolitario) e.nextElement();
			vs.cambio();
		}
	}

}