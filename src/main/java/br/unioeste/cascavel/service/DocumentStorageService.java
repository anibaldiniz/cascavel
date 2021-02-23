package br.unioeste.cascavel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.repository.DocumentoRepository;
import br.unioeste.cascavel.util.exception.DocumentStorageException;

/* ###################################################################
   Esta classe permite fazer upload e download de arquivos através da API
   O arquivo será guardado no disco com o seguinte nome:
   usuario.nome+'-'+solicitacao.id+'-'+documento.nome+'.'+documento.extensao
   ################################################################### */

@Service
public class DocumentStorageService {
    
    private final Path fileStorageLocation;

    @Autowired
    DocumentoRepository documentoRepositorio;

    @Autowired
    public DocumentStorageService(Documento fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getCaminho()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new DocumentStorageException("Náo posso criar o diretório onde o arquivo será criado.", ex);
        }
    }

    public String storeFile(MultipartFile file, Usuario usuario, Solicitacao solicitacao, String documentoNome,
            String documentoExtensao) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";
        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new DocumentStorageException(
                        "Desculpe! O caminho do arquivo contém algum erro " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            fileName = usuario.getNome() + "-" + solicitacao.getId() + '-' + documentoNome + '.' + fileExtension;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Documento doc = documentoRepositorio.getByNome(fileName);
            if (doc != null) {
                doc.setExtensao(file.getContentType());
                doc.setNome(fileName);
                documentoRepositorio.save(doc);
            } else {
                Documento newDoc = new Documento();
                newDoc.setUsuario(usuario);
                newDoc.setExtensao(file.getContentType());
                newDoc.setNome(fileName);

                Path path = new File(fileName).toPath();
                String mimeType = Files.probeContentType(path);

                newDoc.setMimeType(mimeType);
                documentoRepositorio.save(newDoc);
            }
            return fileName;
        } catch (IOException ex) {
            throw new DocumentStorageException(
                    "Não pude armazenar o arquivo " + fileName + ". Por favor tente novamente!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Arquivo não encontrado " + fileName);
        }
    }

    public String getDocumentName(String documentoNome) {
        String caminhoArquivo = documentoRepositorio.getByNome(documentoNome).getConteudo();
        return caminhoArquivo;
    }
}