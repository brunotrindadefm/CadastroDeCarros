import java.io.*;

public class Carro {

	char ativo;
	String codCarro;
	String marca;
	String modelo;
	char fabricacao;
	String origemMarca;
	String categoria;
	float motorizacao;
	int potencia;
	float peso;
	float preco;
	String mesAnoFab;
	static String marcaVetor[] = { "TOYOTA", "HONDA", "VOLKSWAGEN", "CHEVROLET", "FIAT", "HYUNDAI", "BMW",
			"MERCEDES BENS", "RENAULT", "JEEP" };
	static String origemMarcaVetor[] = { "JAPAO", "JAPAO", "ALEMANHA", "EUA", "ITALIA", "COREIA DO SUL", "ALEMANHA",
			"ALEMANHA", "FRANÇA", "EUA" };

	// Método para pesquisar se o código digitado existe no arquivo
	public long pesquisarCarro(String codCarroPesq) {
		long posicaoCursorArquivo = 0;
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			while (true) {
				posicaoCursorArquivo = arqCarro.getFilePointer(); // Posiciona o cursor no início do arquivo
				ativo = arqCarro.readChar();
				codCarro = arqCarro.readUTF();
				marca = arqCarro.readUTF();
				modelo = arqCarro.readUTF();
				fabricacao = arqCarro.readChar();
				origemMarca = arqCarro.readUTF();
				categoria = arqCarro.readUTF();
				motorizacao = arqCarro.readFloat();
				potencia = arqCarro.readInt();
				peso = arqCarro.readFloat();
				preco = arqCarro.readFloat();
				mesAnoFab = arqCarro.readUTF();

				if (codCarroPesq.equals(codCarro) && ativo == 'S') {
					arqCarro.close();
					return posicaoCursorArquivo;
					// Se o código do carro digitado existir no arquivo o long retorna 0
					// Se não existir retorna -1
				}
			}
		} catch (EOFException e) {
			return -1;
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	// Método para salvar registro digitado no arquivo
	public void salvarCarro() {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			arqCarro.seek(arqCarro.length()); // Posicionando cursor no end of file
			arqCarro.writeChar(ativo);
			arqCarro.writeUTF(codCarro);
			arqCarro.writeUTF(marca);
			arqCarro.writeUTF(modelo);
			arqCarro.writeChar(fabricacao);
			arqCarro.writeUTF(origemMarca);
			arqCarro.writeUTF(categoria);
			arqCarro.writeFloat(motorizacao);
			arqCarro.writeInt(potencia);
			arqCarro.writeFloat(peso);
			arqCarro.writeFloat(preco);
			arqCarro.writeUTF(mesAnoFab);

			System.out.println("Dados gravados com sucesso !\n");
			arqCarro.close();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// Método para excluir registro. Transforma ativo que está 'S' em 'N'
	public void desativarCarro(long posicao) {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			arqCarro.seek(posicao);
			arqCarro.writeChar('N');
			arqCarro.close();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// *********************** INCLUSAO *****************************

	public void incluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro;
		boolean erro;

		do {
			do {
				Main.leia.nextLine();
				do {
					System.out.print("Digite o Código do Carro (fim para encerrar) ..: ");
					codCarroChave = Main.leia.nextLine();
					erro = false;
					if (codCarroChave.equalsIgnoreCase("fim")) {
						break;
					}
					if (codCarroChave.length() != 6) {
						System.out.println("\nCódigo invalido, digite placa com 6 caracteres.");
						erro = true;
					} else {
						for (int x = 0; x < codCarroChave.length(); x++) {
							if (x < 3) {
								if (codCarroChave.charAt(x) < 'A' || codCarroChave.charAt(x) > 'Z') {
									System.out.println(
											"\nCódigo invalido, digite 3 primeiros caracteres letras maiusculas.");
									erro = true;
									break;
								}
							} else {
								if (codCarroChave.charAt(x) < '0' || codCarroChave.charAt(x) > '9') {
									System.out.println("\nCódigo invalido, digite 3 últimos caracteres números.");
									erro = true;
									break;
								}
							}
						}
					}
				} while (erro);
				if (codCarroChave.equalsIgnoreCase("fim")) {
					break;
				}
				posicaoRegistro = pesquisarCarro(codCarroChave);

				if (posicaoRegistro >= 0) {
					System.out.println("Código do carro já cadastrado, digite outro valor.");
				}
			} while (posicaoRegistro >= 0);

			if (codCarroChave.equalsIgnoreCase("fim")) {
				break;
			}

			ativo = 'S';
			codCarro = codCarroChave;

			do {
				System.out.print("Digite a marca do carro .......................: ");
				marca = Main.leia.nextLine();
				origemMarca = consistirMarca(marca);
			} while (origemMarca.equalsIgnoreCase("erro"));

			do {
				System.out.print("Digite o modelo do carro ......................: ");
				modelo = Main.leia.nextLine();
				if (modelo.length() < 5) {
					System.out.println("Modelo do carro tem quer ser no minimo 5 caracteres ");
				}
			} while (modelo.length() < 5);
			do {
				System.out.print("Digite a fabricação, nacional ou importado(N/I): ");
				fabricacao = Main.leia.next().charAt(0);
				if (fabricacao != 'N' && fabricacao != 'I') {
					System.out.println("Fabricação de carro invalida, digite nacional ou importado(N/I)  ");
					Main.leia.nextLine();
				}
			} while (fabricacao != 'N' && fabricacao != 'I');

			boolean certo;
			Main.leia.nextLine();
			do {
				certo = false;
				System.out.print("Digite a categoria do carro...................: ");
				categoria = Main.leia.nextLine();
				if (!categoria.equalsIgnoreCase("HATCH") && !categoria.equalsIgnoreCase("SEDÃ")
						&& !categoria.equalsIgnoreCase("SUV") && !categoria.equalsIgnoreCase("PICAPE")
						&& !categoria.equalsIgnoreCase("ESPORTIVO")) {
					certo = true;
					System.out.println("Categoria de carro invalida, digite : HATCH, SEDÃ, SUV, PICAPE ou ESPORTIVO.");
				}
			} while (certo);
			do {
				System.out.print("Digite a motorização do carro (lts)...........: ");
				motorizacao = Main.leia.nextFloat();
				if (motorizacao < 1 || motorizacao > 5) {
					System.out.println("Motorização de carro invalida, digite o valor acima de 1.0 ou menor que 5.0. ");
				}
			} while (motorizacao < 1 || motorizacao > 5);
			do {
				System.out.print("Digite a potencia do carro em cavalos (cv)....: ");
				potencia = Main.leia.nextInt();
				if (potencia <= 0) {
					System.out.println("Potencia de carro invalida, digite o valor acima de 0  ");
					Main.leia.nextLine();
				}
			} while (potencia <= 0);
			do {
				System.out.print("Digite o peso do carro em (kg)................: ");
				peso = Main.leia.nextFloat();
				if (peso < 500) {
					System.out.println("Peso de carro invalida, digite o peso acima de 500kg  ");
				}
			} while (peso < 500);
			do {
				System.out.print("Digite o preço do carro em (R$)...............: ");
				preco = Main.leia.nextFloat();
				if (preco < 10000) {
					System.out.println("Preço de carro invalida, digite o preço acima de R$ 10.000 ");
				}
			} while (preco < 10000);
			Main.leia.nextLine();
			do {
				System.out.print("Digite a data de fabricação do carro .........: ");
				mesAnoFab = Main.leia.nextLine();

			} while (!consistirMesAnoFab(mesAnoFab));

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarCarro();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equalsIgnoreCase("fim"));

	}

	// ************************ ALTERACAO *****************************

	public void alterar() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERACAO DE CARROS  ***************** ");
				System.out.print("Digite a código do Carro que deseja alterar( fim para encerrar ): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equalsIgnoreCase("fim")) {
					break;
				}

				posicaoRegistro = pesquisarCarro(codCarroChave);
				if (posicaoRegistro == -1) {
					System.out.println(
							"\nCódigo de carro não cadastrado no arquivo, digite outro valor. ENTER para continuar...\n");
				}
			} while (posicaoRegistro == -1);

			if (codCarroChave.equalsIgnoreCase("fim")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Marca do carro ..........: " + marca);
				System.out.println(" Origem da marca: " + origemMarca);
				System.out.println("[ 2 ] Modelo do carro .........: " + modelo);
				System.out.println("[ 3 ] Fabricação do carro .....: " + fabricacao);
				System.out.println("[ 4 ] Categoria do carro ......: " + categoria);
				System.out.println("[ 5 ] Motorização do carro ....: " + motorizacao);
				System.out.println("[ 6 ] Potência do carro .......: " + potencia);
				System.out.println("[ 7 ] Peso do carro ...........: " + peso);
				System.out.println("[ 8 ] Categoria do carro ......: " + preco);
				System.out.println("[ 9 ] Data de fabricação ......: " + mesAnoFab);

				do {
					System.out
							.println("Digite o numero do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				} while (opcao < 0 || opcao > 9);

				if (opcao == 1) {
					Main.leia.nextLine();
					do {
						System.out.print("Digite a marca do carro ....................: ");
						marca = Main.leia.nextLine();
						origemMarca = consistirMarca(marca);
					} while (origemMarca.equalsIgnoreCase("erro"));
				} else if (opcao == 2) {
					Main.leia.nextLine();
					do {
						System.out.print("Digite o modelo do carro ...................: ");
						modelo = Main.leia.nextLine();
						if (modelo.length() < 5) {
							System.out.println("Modelo do carro tem quer ser no minimo 5 caracteres ");
						}
					} while (modelo.length() < 5);
				} else if (opcao == 3) {
					do {
						System.out.print("Digite o tipo de fabricação nacional ou importado(N/I)..................: ");
						fabricacao = Main.leia.next().charAt(0);
						if (fabricacao != 'N' && fabricacao != 'I') {
							System.out.println("Fabricação de carro invalida, digite nacional ou importado(N/I)  ");
							Main.leia.nextLine();
						}
					} while (fabricacao != 'N' && fabricacao != 'I');
				} else if (opcao == 4) {
					boolean certo;
					Main.leia.nextLine();
					do {
						certo = false;
						System.out.print("Digite a categoria do carro...................: ");
						categoria = Main.leia.nextLine();
						if (!categoria.equalsIgnoreCase("HATCH") && !categoria.equalsIgnoreCase("SEDÃ")
								&& !categoria.equalsIgnoreCase("SUV") && !categoria.equalsIgnoreCase("PICAPE")
								&& !categoria.equalsIgnoreCase("ESPORTIVO")) {
							certo = true;
							System.out.println(
									"Categoria de carro invalida, digite : HATCH, SEDÃ, SUV, PICAPE ou ESPORTIVO.");
						}
					} while (certo);
				} else if (opcao == 5) {
					do {
						System.out.print("Digite a motorização do carro (lts)...................: ");
						motorizacao = Main.leia.nextFloat();
						if (motorizacao < 1 || motorizacao > 5) {
							System.out.println(
									"Motorização de carro invalida, digite o valor acima de 1.0 ou menor que 5.0. ");
						}
					} while (motorizacao < 1 || motorizacao > 5);
				} else if (opcao == 6) {
					do {
						System.out.print("Digite a potencia do carro em cavalos (cv)...................: ");
						potencia = Main.leia.nextInt();
						if (potencia <= 0) {
							System.out.println("Potencia de carro invalida, digite o valor acima de 0  ");
							Main.leia.nextLine();
						}
					} while (potencia <= 0);
				} else if (opcao == 7) {
					do {
						System.out.print("Digite o peso do carro em (kg)...................: ");
						peso = Main.leia.nextFloat();
						if (peso < 500) {
							System.out.println("Peso de carro invalida, digite o peso acima de 500kg  ");
						}
					} while (peso < 500);
				} else if (opcao == 8) {
					do {
						System.out.print("Digite o preço do carro em (R$)...................: ");
						preco = Main.leia.nextFloat();
						if (preco < 10000) {
							System.out.println("Preço de carro invalida, digite o preço acima de R$ 10.000 ");
						}
					} while (preco < 10000);
				} else if (opcao == 9) {
					Main.leia.nextLine();
					do {
						System.out.print("Digite a data de fabricação do carro .........: ");
						mesAnoFab = Main.leia.nextLine();

					} while (!consistirMesAnoFab(mesAnoFab));

				} else {
					break;
				}

				System.out.println();
			} while (opcao != 0);

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarCarro(posicaoRegistro);
					salvarCarro();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("fim"));
	}

	// ************************ EXCLUSAO *****************************

	public void excluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE CARROS  ***************** ");
				System.out.print("Digite o código carro do Carro que deseja excluir ( fim para encerrar ): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equalsIgnoreCase("fim")) {
					break;
				}

				posicaoRegistro = pesquisarCarro(codCarroChave);
				if (posicaoRegistro == -1) {
					System.out.println(
							"\nCódigo do carro não cadastrado no arquivo, digite outro valor.\nENTER para continuar...");
				}
			} while (posicaoRegistro == -1);

			if (codCarroChave.equalsIgnoreCase("fim")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("Marca do carro ..........: " + marca);
			System.out.println(" Origem da marca: " + origemMarca);
			System.out.println("Modelo do carro .........: " + modelo);
			System.out.println("Fabricação do carro .....: " + fabricacao);
			System.out.println("Categoria do carro ......: " + categoria);
			System.out.println("Motorização do carro ....: " + motorizacao);
			System.out.println("Potência do carro .......: " + potencia);
			System.out.println("Peso do carro ...........: " + peso);
			System.out.println("Categoria do carro ......: " + preco);
			System.out.println("Data de fabricação ......: " + mesAnoFab);

			do {
				System.out.print("\nConfirma a exclusao deste Carro (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarCarro(posicaoRegistro);
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("fim"));
	}

	// ************************ CONSULTA *****************************
	public void consultar() {
		RandomAccessFile arqCarro;
		byte opcao;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE CARROS  ***************** ");
				System.out.println(" [1] LISTAR TODOS OS CARROS DA MARCA DIGITADA ");
				System.out.println(" [2] LISTAR TODOS OS CARROS DE UM ANO DE FABRICAÇÃO DIGITADO ");
				System.out.println(" [3] LISTAR TODOS OS CARROS DA FAIXA DE PREÇO DIGITADA ");
				System.out.println(" [4] LISTAR TODOS OS CARROS");
				System.out.println(" [0] SAIR ");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 4) {
					System.out.println("Opcção invalida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 4);

			if (opcao == 1) {
				String marcaDigitada;

				Main.leia.nextLine();
				System.out.println("Digite a marca:");
				marcaDigitada = Main.leia.nextLine();

				try {
					arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo = arqCarro.readChar();
						codCarro = arqCarro.readUTF();
						marca = arqCarro.readUTF();
						modelo = arqCarro.readUTF();
						fabricacao = arqCarro.readChar();
						origemMarca = arqCarro.readUTF();
						categoria = arqCarro.readUTF();
						motorizacao = arqCarro.readFloat();
						potencia = arqCarro.readInt();
						peso = arqCarro.readFloat();
						preco = arqCarro.readFloat();
						mesAnoFab = arqCarro.readUTF();
						if (marcaDigitada.equalsIgnoreCase(marca) && ativo == 'S') {
							imprimirCarro();
						}
					}

				} catch (EOFException e) {
					System.out.println();
					System.out.println("\n fim de relatório - ENTER para continuar...\n");
					Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
			}

			else if (opcao == 2) {
				String anoFabDigitada;
				Main.leia.nextLine();
				System.out.println("Digite o ano de fabricação:");
				anoFabDigitada = Main.leia.nextLine();

				try {
					arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo = arqCarro.readChar();
						codCarro = arqCarro.readUTF();
						marca = arqCarro.readUTF();
						modelo = arqCarro.readUTF();
						fabricacao = arqCarro.readChar();
						origemMarca = arqCarro.readUTF();
						categoria = arqCarro.readUTF();
						motorizacao = arqCarro.readFloat();
						potencia = arqCarro.readInt();
						peso = arqCarro.readFloat();
						preco = arqCarro.readFloat();
						mesAnoFab = arqCarro.readUTF();
						if (anoFabDigitada.equalsIgnoreCase(mesAnoFab.substring(3)) && ativo == 'S') {
							imprimirCarro();
						}
					}

				} catch (EOFException e) {
					System.out.println();
					System.out.println("\n fim de relatório - ENTER para continuar...\n");
					Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}

			}

			else if (opcao == 3) {
				float vlrMin;
				float vlrMax;
				System.out.println("Digite o valor mínimo do carro: ");
				vlrMin = Main.leia.nextFloat();

				System.out.println("Digite o valor máximo do carro: ");
				vlrMax = Main.leia.nextFloat();
				try {
					arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo = arqCarro.readChar();
						codCarro = arqCarro.readUTF();
						marca = arqCarro.readUTF();
						modelo = arqCarro.readUTF();
						fabricacao = arqCarro.readChar();
						origemMarca = arqCarro.readUTF();
						categoria = arqCarro.readUTF();
						motorizacao = arqCarro.readFloat();
						potencia = arqCarro.readInt();
						peso = arqCarro.readFloat();
						preco = arqCarro.readFloat();
						mesAnoFab = arqCarro.readUTF();
						if (preco > vlrMin && preco < vlrMax && ativo == 'S') {
							imprimirCarro();
						}
					}

				} catch (EOFException e) {
					System.out.println();
					System.out.println("\n fim de relatório - ENTER para continuar...\n");
					Main.leia.nextLine();
					Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}

			}

			else if (opcao == 4) {
				try {
					arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo = arqCarro.readChar();
						codCarro = arqCarro.readUTF();
						marca = arqCarro.readUTF();
						modelo = arqCarro.readUTF();
						fabricacao = arqCarro.readChar();
						origemMarca = arqCarro.readUTF();
						categoria = arqCarro.readUTF();
						motorizacao = arqCarro.readFloat();
						potencia = arqCarro.readInt();
						peso = arqCarro.readFloat();
						preco = arqCarro.readFloat();
						mesAnoFab = arqCarro.readUTF();
						if (ativo == 'S') {
							imprimirCarro();
						}
					}

				} catch (EOFException e) {
					System.out.println();
					System.out.println("\n fim de relatório - ENTER para continuar...\n");
					Main.leia.nextLine();
					Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
			} else {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

		} while (opcao != 0);
	}

	// Método para imprimir o cabeçalho
	public void imprimirCabecalho() {
		System.out.println();
		System.out.println(
				"-CodCarro- ----Marca----- --Modelo--  ----Origem---- -Fab- -Categoria- -Mot- --Pot-- --Peso-- --Preço-- -Mes&Ano-");

	}

	// Método para imprimir o registro
	public void imprimirCarro() {
		System.out.println(formatarString("  " + codCarro, 10) + " " + formatarString(marca, 14) + " "
				+ formatarString(modelo, 15) + " " + formatarString(origemMarca, 10) + " "
				+ formatarString(Character.toString(fabricacao), 5) + " " + formatarString(categoria, 11) + " "
				+ formatarString(String.valueOf(motorizacao), 5) + " " + formatarString(String.valueOf(potencia), 7)
				+ " " + formatarString(String.valueOf(peso), 8) + " " + formatarString(String.valueOf(preco), 9) + " "
				+ formatarString(mesAnoFab, 9));

		// formatarString(variável, tamanho que queremos que ela ocupe)
	}

	// Método para formatar string para não ficar muito bagunçado o cabeçalho
	public static String formatarString(String texto, int tamanho) {
		if (texto.length() > tamanho) {
			texto = texto.substring(0, tamanho);
		} else {
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}

	// Método para validação do mês e ano de fabricação
	public static boolean consistirMesAnoFab(String mes_ano) {

		if (mes_ano.length() != 7 || mes_ano.charAt(2) != '/') {
			System.out.println("Data invalida, digite data com 7 caracteres no formato MM/AAAA.");
			return false;
		}

		int mes = Integer.parseInt(mes_ano.substring(0, 2));
		int ano = Integer.parseInt(mes_ano.substring(3));

		if (mes < 1 || mes > 12) {
			System.out.println("Data invalida, digite mes entre 1 e 12.");
			return false;
		}
		if (ano < 1980 || ano > 2024) {
			System.out.println("Data invalida, digite o ano maior que 1980 e menor ou igual a 2024.");
			return false;
		}

		return true;
	}

	// Método que retorna o valor de origemMarca relacionado com a marca digitada
	public static String consistirMarca(String marca) {
		boolean marcaEncontrada = false;
		String origem = "";
		for (int i = 0; i <= 9; i++) {
			if (marca.equalsIgnoreCase(marcaVetor[i])) {
				System.out.println("País de origem da marca informada: " + origemMarcaVetor[i]);
				marcaEncontrada = true;
				origem = origemMarcaVetor[i];
				break;
			}
		}
		if (!marcaEncontrada) {
			System.out.println("ERRO.");
			origem = "erro";
		}
		return origem;
	}

}