package ProjetoCryptoBot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;

public class CryptoBot {
	public static void main(String[] args) throws Exception{
		
		try {
		
		//Criaando conexão com api da coin market cap
		String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=50&convert=BRL";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-CMC_PRO_API_KEY", "fa67fa72-79f0-4978-80a9-d78d5277c3ee");
		con.setRequestProperty("Accept", "application/json");
		int repostaRequisicao = con.getResponseCode();
		System.out.println("Status da requisição: " + repostaRequisicao);
		
		//Lendo arquivo json
		BufferedReader leitor = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String lietorLinhas;
		StringBuffer resposta = new StringBuffer();
		while((lietorLinhas = leitor.readLine()) != null) {
			resposta.append(lietorLinhas);
		}
		leitor.close();
		
		//Extração do arquivo
		JSONObject myResposta = new JSONObject(resposta.toString());
		JSONArray data = myResposta.getJSONArray("data");
		double comparador = Double.NEGATIVE_INFINITY;
		double porcentagem24h = 0;
		DecimalFormat df = new DecimalFormat("0.00000");
		String formatPorcentagem = "";
		String melhorCrypto = "";
		for(int i = 0; i < data.length(); i++) {
			JSONObject crypto = data.getJSONObject(i);
			porcentagem24h = crypto.getJSONObject("quote").getJSONObject("BRL").getDouble("percent_change_24h");
			double valor = crypto.getJSONObject("quote").getJSONObject("BRL").getDouble("price");
			if( porcentagem24h > comparador) {
				comparador = porcentagem24h;
				formatPorcentagem = df.format(comparador);
				melhorCrypto = crypto.getString("name");
			}
			
		}
		System.out.println("A melhor criptomoeda para se investir no momento é " + melhorCrypto + ", pois sua porcentagem de alta é de: " + formatPorcentagem + "%");
		
		} catch (Exception e) {
			e.printStackTrace();
		
		
		
		}
	} 
}	
