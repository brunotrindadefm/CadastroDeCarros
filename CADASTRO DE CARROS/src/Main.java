import java.util.*;

public class Main {

	static Scanner leia = new Scanner(System.in);

	public static void main(String[] args) {
		Carro carro = new Carro();
		byte opcao = -1;

		do {
			do {
				System.out.println("\n ***************  CADASTRO DE CARROS  ***************** ");
				System.out.println(" [1] INCLUIR CARROS ");
				System.out.println(" [2] ALTERAR CARROS ");
				System.out.println(" [3] CONSULTAR CARROS ");
				System.out.println(" [4] EXCLUIR CARROS ");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = leia.nextByte();
				if (opcao < 0 || opcao > 4) {
					System.out.println("Opcão invalida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 4);

			if (opcao == 0) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
			} else if (opcao == 1) {
				carro.incluir();
			} else if (opcao == 2) {
				carro.alterar();
			} else if (opcao == 3) {
				carro.consultar();
			} else {
				carro.excluir();
			}
		} while (opcao != 0);
	}

}
