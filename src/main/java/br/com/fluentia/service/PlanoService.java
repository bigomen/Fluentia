package br.com.fluentia.service;

import br.com.fluentia.dto.PlanoDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Plano;
import br.com.fluentia.model.PlanoIugu;
import br.com.fluentia.repository.PlanoRepository;
import br.com.fluentia.type.StatusPlanoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanoService extends AbstractService {

	@Autowired
	private PlanoRepository planoRepository;

	@Autowired
	private IuguService iuguService;

	public void criar(PlanoDto dto){
		if(planoRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
		if(dto.getPreco().intValue() > 0){
			PlanoIugu iugu = dto.dtoToIugu();
			dto.setCodigoIugu(iuguService.criarPlanoIugu(iugu));
		}
		try {
			planoRepository.save(dto.dtoToModel());
		}catch (Exception e){
			throw new InternalErroException();
		}

	}



    public void editar(PlanoDto dto){
		Optional<Plano> planoBackup = planoRepository.findById(dto.getId());
		if(planoBackup.isEmpty()) throw new NotFoundException("Plano");
		if(planoRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
		dto.setPreco(planoBackup.get().getPreco());
		if(planoBackup.get().getPreco().intValueExact() > 0){
			dto.setCodigoIugu(planoRepository.getCodigo(dto.getId()));
			Map iuguRequest = dto.dtoToMapIugu();
			iuguService.atualizarPlanoIugu(iuguRequest);
		}
		dto.setAtivo(planoBackup.get().getAtivo());
		try {
			planoRepository.save(dto.dtoToModel());
		}catch (Exception e){
			throw new BusinessException("Erro ao editar plano");
		}
	}

	public List<PlanoDto> listarAtivos() {
		Collection<Plano> planos = planoRepository.findAllByAtivoOrderByNomeAsc(StatusPlanoEnum.ATIVO.getStatus());
		return planos.stream().map(Plano::modelToDto).collect(Collectors.toList());
	}

	public List<PlanoDto> listarAtivosPublico() {
		Collection<Plano> planos = planoRepository.findAllByAtivoAndPublicoOrderByNomeAsc(StatusPlanoEnum.ATIVO.getStatus(), true);
		return planos.stream().map(Plano::modelToDto).collect(Collectors.toList());
	}

	@Transactional
	public void desativar(Long idPlano){
		if(!planoRepository.existsById(idPlano)) throw new NotFoundException("Plano");
		planoRepository.alterarStatus(idPlano, StatusPlanoEnum.INATIVO.getStatus());
	}

	@Transactional
	public void ativar(Long idPlano){
		if(!planoRepository.existsById(idPlano)) throw new NotFoundException("Plano");
		planoRepository.alterarStatus(idPlano, StatusPlanoEnum.ATIVO.getStatus());
	}

	public List<PlanoDto> buscarTodos() {
		Aluno aluno = this.verificarAluno();
		List<Plano> planos;
		if(Objects.nonNull(aluno)){

			planos = planoRepository.findAllByIdEmpresaOrderByNomeAsc(aluno.getIdEmpresa());
		}else {
			planos = planoRepository.findAllByOrderByNomeAsc();
		}

		return planos.stream().map(Plano::modelToDto).collect(Collectors.toList());
	}

	public PlanoDto buscarPorCodigo(String codigo) {
		Plano plano = planoRepository.findByCodigoIugu(codigo)
				.orElseThrow(() -> new NotFoundException("Plano"));
		return plano.modelToDto();
	}

	public PlanoDto buscarPorId(Long id) {
		Plano plano = planoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Plano"));
		return plano.modelToDto();
	}

	public void editarValorPlano(Map<String,String> body) {
		String codigo = body.get("codigo");
		BigDecimal novoValor = new BigDecimal(body.get("novoValor"));
		Optional<Plano> plano = planoRepository.findByCodigoIugu(codigo);
		if(plano.isEmpty()) throw new NotFoundException("Plano");
		Map planoIugu = iuguService.buscarPlanoIugu(plano.get().getCodigoIugu());
		Map prices = (Map) planoIugu.get("prices");
		prices.put("value_cents", novoValor.intValue());
		planoIugu.put("prices", prices);
		iuguService.atualizarPlanoIugu(planoIugu);
		plano.get().setPreco(novoValor);
		planoRepository.save(plano.get());
	}

	public List<PlanoDto> planosPorEmpresa(Long idEmpresa){
		Aluno aluno = this.verificarAluno();
		List<Plano> planos;
		if(Objects.nonNull(aluno)){
			planos = planoRepository.findAllByIdEmpresaAndAtivoOrderByNomeAsc(idEmpresa, StatusPlanoEnum.ATIVO.getStatus());
		}else {
			planos = planoRepository.findAllByIdEmpresaOrderByNomeAsc(idEmpresa);
		}
		return planos.stream().map(Plano::modelToDto).collect(Collectors.toList());
	}

	public void inserirTodosIugu(){
		List<Plano> planos = planoRepository.planosSemIugu();
		if(!planos.isEmpty()){
			List<Long> idsErro = new ArrayList<>();
			for(Plano p : planos){
				PlanoIugu planoIugu = p.modelToDto().dtoToIugu();
				try {
					p.setCodigoIugu(iuguService.criarPlanoIugu(planoIugu));
					planoRepository.save(p);
				}catch (Exception e){
					idsErro.add(p.getId());
				}
			}
			System.out.println(idsErro);
		}
	}
}
