package com.project.controllers;

import com.project.data.DAOComponents;
import com.project.data.DAOComponentsImpl;
import com.project.diagram.Diagram;
import com.project.elements.timers.ModelSimpleTimer;
import com.project.elements.timers.ModelTimer;
import com.project.elements.timers.TimerProperties;
import com.project.json.serialize.GsonDiagramSerializer;
import com.project.model.component.Component;
import com.project.utils.ZipUtils;
import com.project.vhdl.VHDLStructureConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ComponentController {

    /**
     * Fixme very smell code!
     */
    private ModelTimer timer;

    public synchronized ModelTimer getTimer() {
        return timer;
    }

    public synchronized void setTimer(ModelTimer timer) {
        this.timer = timer;
    }

    @Autowired
    DAOComponents components;

    @RequestMapping(value = "/controller/components", method = RequestMethod.GET)
    @ResponseBody
    public String getComponents(){
        System.out.println("come!");
        String body = components.getAllComponents().stream().map(Component::getJsonScript).collect(Collectors.joining(" ,"));
        return "[" + body + "]";
    }

    @RequestMapping(value = "/controller/component/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveComponent(@RequestBody String jsonComponent){
        Component component = components.addComponent(jsonComponent);
        return "{\"number\": " + component.getNumber()+"}";
    }

    @RequestMapping(value = "/controller/component/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String getComponent(@PathVariable("number") int number){
        System.out.println("get component!");
        Component component = components.getComponent(number);
        return component.getJsonScript();
    }

    @RequestMapping(value = "/controller/diagram/properties", method = RequestMethod.POST)
    @ResponseBody
    public String setDiagramProperties(@RequestBody String jsonProperties){
        TimerProperties properties = components.getTimerProperties(jsonProperties);
        ModelTimer timer = new ModelSimpleTimer();
        timer.setStep(properties.getStep());
        timer.setFinishTime(properties.getDuration());
        setTimer(timer);
        return "{ \"success\" : \"true\"}";
    }

    /*
     * Download a file from server
     */
    @RequestMapping(value="/controller/{number}/structure/{type}", method = RequestMethod.GET)
    public void getStructue(@PathVariable("number") int number, @PathVariable("type") String type, HttpServletResponse response) throws IOException {

        if(!"vhdl".equals(type)){
            System.err.println("Something wrong!");
        }
        Component componentForVhdl = components.getComponent(number);
        Set<File> files = new VHDLStructureConfigurator().configure(componentForVhdl);

        String zipPath = "\\component" + componentForVhdl.getNumber() +".zip";
        new ZipUtils().createZip(zipPath , files);

        File file = new File(zipPath);
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
        System.out.println("mimetype : "+mimeType);
        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        response.setContentLength((int)file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/controller/diagram", method = RequestMethod.POST)
    @ResponseBody
    public String getDiagram(@RequestBody String jsonComponent){
        Component component = components.getComponent(jsonComponent);
        Diagram diagram = components.getDiagram(component, getTimer());
        System.out.println("get diagram!");
        System.out.println(diagram);
        return new GsonDiagramSerializer().serializeComponents(diagram);
    }

    private String getJsonWrapper(String body){
        return "{" + body + "}";
    }
}
