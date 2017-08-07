package br.com.onesystem.war.view;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.builder.DialogoPagamentoRecebimentoVariasMoedasBuilder;
import br.com.onesystem.war.service.ContaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DialogoPagamentoRecebimentoVariasMoedasView implements Serializable {

    private Conta contaDeOrigem;
    private List<Conta> contaLista;
    private DialogoPagamentoRecebimentoVariasMoedasBuilder dlg;
    private DialogoPagamentoRecebimentoVariasMoedasBuilder dlgSelecionado;
    private List<DialogoPagamentoRecebimentoVariasMoedasBuilder> dlgLista;

    @ManagedProperty("#{contaService}")
    private ContaService service;

    @PostConstruct
    public void init() {
        contaLista = service.buscarContas();
        dlgLista = new ArrayList<DialogoPagamentoRecebimentoVariasMoedasBuilder>();
        limpar();
    }

    public void abrirDialogo(Conta conta) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        if (conta != null) {
            System.out.println("Conta: " + conta.getMoeda().getNome());
            this.contaDeOrigem = conta;
            System.out.println("Conta or: " + contaDeOrigem.getMoeda().getNome());
            RequestContext.getCurrentInstance().openDialog("dialogoPagamentoRecebimentoVariasMoedas", opcoes, null);
        } else {
            ErrorMessage.print(new BundleUtil().getMessage("conta_not_null"));
        }
    }

    public void selecionar() {
        dlg = new DialogoPagamentoRecebimentoVariasMoedasBuilder(dlgSelecionado);
    }

    public void add() {
        if (valida()) {
            return;
        }
        dlgLista.add(dlg);
        limpar();
    }

    private void limpar() {
        dlg = new DialogoPagamentoRecebimentoVariasMoedasBuilder();
        dlgSelecionado = new DialogoPagamentoRecebimentoVariasMoedasBuilder();
    }

    private boolean valida() {
        if (dlg.getValor().equals(BigDecimal.ZERO)) {
            ErrorMessage.print("O valor deve ser maior que zero.");
            return true;
        }
        if (dlg.getValorConvertido().equals(BigDecimal.ZERO)) {
            ErrorMessage.print("O valor convertido deve ser maior que zero.");
            return true;
        }
        return false;
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!dlgLista.isEmpty()) {
            for (DialogoPagamentoRecebimentoVariasMoedasBuilder objeto : dlgLista) {
                if (objeto.getId() >= id) {
                    id = objeto.getId() + 1;
                }
            }
        }
        return id;
    }

    public DialogoPagamentoRecebimentoVariasMoedasBuilder getDlgSelecionado() {
        return dlgSelecionado;
    }

    public void setDlgSelecionado(DialogoPagamentoRecebimentoVariasMoedasBuilder dlgSelecionado) {
        this.dlgSelecionado = dlgSelecionado;
    }

    public List<Conta> getContaLista() {
        return contaLista;
    }

    public void setContaLista(List<Conta> contaLista) {
        this.contaLista = contaLista;
    }

    public ContaService getService() {
        return service;
    }

    public void setService(ContaService service) {
        this.service = service;
    }

    public Conta getContaDeOrigem() {
        return contaDeOrigem;
    }

    public void setContaDeOrigem(Conta contaDeOrigem) {
        this.contaDeOrigem = contaDeOrigem;
    }

    public DialogoPagamentoRecebimentoVariasMoedasBuilder getDlg() {
        return dlg;
    }

    public void setDlg(DialogoPagamentoRecebimentoVariasMoedasBuilder dlg) {
        this.dlg = dlg;
    }

    public List<DialogoPagamentoRecebimentoVariasMoedasBuilder> getDlgLista() {
        return dlgLista;
    }

    public void setDlgLista(List<DialogoPagamentoRecebimentoVariasMoedasBuilder> dlgLista) {
        this.dlgLista = dlgLista;
    }
}
