package br.com.fluentia.service;

import br.com.fluentia.dto.EmpresaDto;
import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Empresa;
import br.com.fluentia.repository.EmpresaRepository;
import br.com.fluentia.utils.GeneratePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class EmpresaService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmpresaRepository empresaRepository;

    public void criarEmpresa(EmpresaDto dto){
        if(empresaRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
        empresaRepository.save(dto.dtoToModel());
    }

    public void atualizarEmpresa(EmpresaDto dto){
        if(!empresaRepository.existsById(dto.getId())) throw new NotFoundException("Empresa");
        if(empresaRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
        empresaRepository.save(dto.dtoToModel());
    }

    public void apagarEmpresa(Long id){
        if(!empresaRepository.existsById(id)) throw new NotFoundException("Empresa");
        empresaRepository.deleteById(id);
    }

    public EmpresaDto detalhesEmpresa(Long id){
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if(empresa.isEmpty()) throw new NotFoundException("Empresa");
        return empresa.get().modelToDto();
    }

    public Page<EmpresaDto> listaEmpresas(PaginacaoDto params){
        Page<Empresa> empresas = empresaRepository.findAll(GeneratePageable.build(params, Sort.by("nome").ascending()));
        return empresas.map(Empresa::modelToDto);
    }

    public List<EmpresaDto> listaAtivos(){
        List<Empresa> dtos = empresaRepository.listaAtivos();
        return dtos.stream().map(Empresa::modelToDto).collect(Collectors.toList());
    }

    public List<Long> listaSimples(){
        return empresaRepository.listaIdEmpresas();
    }
}
