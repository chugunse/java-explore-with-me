import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Connector {
    private final String init = "enterprise /D\"D:\\1Cv77\\baza1\" /N\"Директор\" /P";
    private DispatchPtr app;

    private boolean openConnection(){
        try {
            Ole32.CoInitialize();
            app = new DispatchPtr("V77.Application");
            app.invoke("Initialize",app.get("RMTrade"),init,"NO_SPLASH_SHOW");
            return true;
        }catch (Exception ex){
            System.out.println("Connection error: "+ex.toString());
        }
        return false;
    }

    String getDocumentsData(String docType, String period){
        String result;
        JSONObject jsonObject = new JSONObject();
        JSONObject parameters = new JSONObject();
        parameters.put("type", docType);
        parameters.put("period", period);
        jsonObject.put("parameters", parameters);
        JSONArray resultArray = new JSONArray();
        if (openConnection()){
            try {
                DispatchPtr docItems = (DispatchPtr) app.invoke("ВыборкаДокументов", docType, period);
                while ((Double) docItems.invoke("GetDocument") > 0.0){
                    DispatchPtr item = (DispatchPtr) docItems.invoke("CurrentDocument");
                    readDocumentItemData(item, resultArray);
                }
            }catch (Exception ex) {
                System.out.println("Error: " + ex.toString());
                jsonObject.put("error", ex.toString());
            }finally {
                closeConnection();
            }
        }
        app = null;
        jsonObject.put("result", resultArray);
        result = jsonObject.toString();
        return result;
    }

    private void readDocumentItemData(DispatchPtr item, JSONArray array) throws COMException {
        int isProcessed = 0;
        if ((Double) item.invoke("IsTransacted") != 0.0) isProcessed = 1;
        int isDeleted = 0;
        if ((Double) item.invoke("DeleteMark") != 0.0) isDeleted = 1;
        String guid = app.invoke("ИдентификаторЭлемента", item).toString();
        String date = app.invoke("ДатаДокумента", item).toString();
        String contractor = app.invoke("КонтрагентВДокументе", item).toString();
        String sum = app.invoke("СуммаДокумента", item).toString();

        JSONObject jsonItem = new JSONObject();
        jsonItem.put("guid", guid);
        jsonItem.put("isProcessed", isProcessed);
        jsonItem.put("isDeleted", isDeleted);
        jsonItem.put("number", item.get("DocNum").toString());
        jsonItem.put("date", date);
        jsonItem.put("contractor", contractor);
        jsonItem.put("company", description((DispatchPtr) item.get("Фирма")));
        jsonItem.put("warehouse", description((DispatchPtr) item.get("Склад")));
        jsonItem.put("sum", sum);

        array.add(jsonItem);
    }

    private String description(DispatchPtr item) throws COMException{
        return item.get("Description").toString();
    }

    private void closeConnection(){
        try {
            Ole32.CoUninitialize();
        }catch (Exception ex){
            System.out.println("closeConnection error: "+ex.toString());
        }
    }
}
