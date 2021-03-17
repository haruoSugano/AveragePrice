package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){//c:\temp\product.txt
			
			String productCsv = br.readLine();
			while(productCsv != null) {
				
				String [] fields = productCsv.split(",");
				
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));//Forma reduzida!!
				//String name = fields[0];
				//double price = Double.parseDouble(fields[1]);
				//list.add(new Product(name, price));
				
				productCsv = br.readLine();
			}
			//achando a media dos preço do produto
			double avg = list.stream()//converte para stream
					.map(p -> p.getPrice())//para cada produto p vou querer produto price
					.reduce(0.0/*começar com o zero*/, (x,y) -> x + y)/*Soma de todo mundo*/ / list.size();
			
			System.out.println("Average price: " + String.format("%.2f", avg));
			
			//achando os produtos abaixo  da media
			// Comparador de string independente de se ela e maiuscula ou minuscula
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());//declarar aqui em cima para ficar mais limpa
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)//filtrar valores  p.getPrice() menor que a media
					.map(p -> p.getName())//pegar o nome de cada produto "nome filtrados"
					.sorted(comp.reversed())//Ordena decrescente de nome "Inversa" comp.reversed
					.collect(Collectors.toList());//transformar essa stream em list
			
			names.forEach(System.out::println);
			
		}
		catch(IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

}
