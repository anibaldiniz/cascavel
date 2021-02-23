package br.unioeste.cascavel.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.service.DocumentStorageService;
import br.unioeste.cascavel.service.DocumentosService;

@Controller
public class DocumentoController {

    @Autowired
    private DocumentosService documentoService;

    @GetMapping("/documentos")
    public String viewHomePage(Model model) {
        model.addAttribute("ListaDocumentos", documentoService.getAllDocumentos());
        return "documentos";
    }

    @GetMapping("/formIncluirDocumento")
    public String formIncluirDocumento(Model model) {
        Documento documento = new Documento();
        model.addAttribute("documento", documento);
        return "novo_documento";
    }

    @PostMapping("/incluirDocumento")
    public String incluir(@ModelAttribute("documento") Documento documento) {
        documentoService.save(documento);
        return "redirect:/documentos";
    }

    @GetMapping("/formAlterarDocumento/{id}")
    public String alterar(@PathVariable(value = "id") long id, Model model) {
        Documento documento = documentoService.getDocumentoById(id);
        model.addAttribute("documento", documento);
        return "alterar_documento";
    }

    @GetMapping("/apagarDocumento/{id}")
    public String apagar(@PathVariable(value = "id") long id, Model model) {
        this.documentoService.apagarDocumentoById(id);
        return "redirect:/documentos";
    }

    /*
     * ################################################################### para
     * fazer upload e download de arquivos
     * https://dzone.com/articles/java-springboot-rest-api-to-uploaddownload-file-on
     * ###################################################################
     */

    @Autowired
    private DocumentStorageService documneStorageService;

    @PostMapping("/uploadFile")
    public Documento uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("usuario") Usuario usuario, @RequestParam("solicitacao") Solicitacao solicitacao, @RequestParam("documentoNome") String documentoNome , @RequestParam("documentoExtensao") String documentoExtensao) {
        String fileName = documneStorageService.storeFile(file, usuario, solicitacao, documentoNome, documentoExtensao);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(fileName).toUriString();
        Documento documento = new Documento();
        documento.setCaminho(fileDownloadUri);
        documento.setNome(fileName);
        documento.setMimeType(file.getContentType());
        return documento;
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("usuario") Usuario usuario, @RequestParam("solicitacao") Solicitacao solicitacao,
            @RequestParam("documentoNome") String documentoNome,
            @RequestParam("documentoExtensao") String documentoExtensao, HttpServletRequest request)  {
        String fileName = documneStorageService.getDocumentName(documentoNome);
        Resource resource = null;
        if (fileName != null && !fileName.isEmpty()) {
            try {
                resource = documneStorageService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
