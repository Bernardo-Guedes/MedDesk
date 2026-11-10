import { useState } from "react";
import { UserRound } from "lucide-react";
import { useNavigate } from "react-router";
import HistoryEntry from "@/components/HistoryEntry";
import PatientHeader from "@/components/PatientHeader";
import InfoField from "@/components/ui/InfoField";
import { DangerButton, PrimaryButton } from "@/components/ui/button";

type AbaHistorico = "consultas" | "internacoes";

const dadosIniciais = {
  cpf: "234.567.890-11",
  nascimento: "12/03/2019",
  telefone: "+55 (31) 99777-1122",
  email: "familia.martins@email.com",
  endereco: "Av. Afonso Pena, 456 - Centro, Belo Horizonte - MG",
};

type DadosPaciente = typeof dadosIniciais;

function DetalhesPaciente() {
  const [abaAtiva, setAbaAtiva] = useState<AbaHistorico>("consultas");
  const [dados, setDados] = useState<DadosPaciente>(dadosIniciais);
  const [rascunho, setRascunho] = useState<DadosPaciente>(dadosIniciais);
  const [editando, setEditando] = useState(false);
  const navigate = useNavigate();

  const iniciarEdicao = () => {
    setRascunho(dados);
    setEditando(true);
  };

  const salvar = () => {
    setDados(rascunho);
    setEditando(false);
  };

  const atualizar = (campo: keyof DadosPaciente) => (valor: string) =>
    setRascunho((atual) => ({ ...atual, [campo]: valor }));

  const valores = editando ? rascunho : dados;

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <UserRound size={40} aria-hidden="true" />
        <h1>Detalhes do Paciente</h1>
      </div>

      <div className="mx-auto flex max-w-5xl flex-col gap-8 p-5 md:p-8">
        <div>
          <PrimaryButton
            texto="Voltar"
            onClick={() => navigate("/admin/pacientes")}
          />
        </div>
        <section className="rounded-2xl bg-white p-6 shadow-sm md:p-8">
          <div className="mb-6 flex flex-wrap items-center justify-between gap-3">
            <h2 className="text-xl font-semibold text-principal">
              Informações do Paciente
            </h2>
            {editando ? (
              <div className="flex gap-3">
                <DangerButton
                  texto="Cancelar"
                  onClick={() => setEditando(false)}
                />
                <PrimaryButton texto="Salvar" onClick={salvar} />
              </div>
            ) : (
              <PrimaryButton texto="Editar dados" onClick={iniciarEdicao} />
            )}
          </div>

          <PatientHeader
            nome="Alice Martins"
            iniciais="AM"
            idade="7 anos"
            status="Internado"
            statusAtendimento="emAndamento"
          />

          <hr className="my-6 border-slate-200" />

          <dl className="grid gap-5 md:grid-cols-2">
            <InfoField
              rotulo="CPF"
              valor={valores.cpf}
              editando={editando}
              onChange={atualizar("cpf")}
            />
            <InfoField
              rotulo="Data de Nascimento"
              valor={valores.nascimento}
              editando={editando}
              onChange={atualizar("nascimento")}
            />
            <InfoField
              rotulo="Telefone"
              valor={valores.telefone}
              editando={editando}
              onChange={atualizar("telefone")}
            />
            <InfoField
              rotulo="E-mail para contato"
              valor={valores.email}
              editando={editando}
              onChange={atualizar("email")}
            />
            <div className="md:col-span-2">
              <InfoField
                rotulo="Endereço"
                valor={valores.endereco}
                editando={editando}
                onChange={atualizar("endereco")}
              />
            </div>
          </dl>
        </section>

        <section>
          <h2 className="mb-5 text-xl font-semibold text-principal">
            Histórico de Atendimentos
          </h2>

          <div
            className="mb-5 flex w-fit gap-2 rounded-xl bg-slate-200 p-1"
            role="tablist"
            aria-label="Tipo de histórico"
          >
            <button
              id="aba-consultas"
              type="button"
              role="tab"
              aria-selected={abaAtiva === "consultas"}
              aria-controls="painel-consultas"
              onClick={() => setAbaAtiva("consultas")}
              className={`rounded-lg px-4 py-2 font-semibold transition-colors ${
                abaAtiva === "consultas"
                  ? "bg-white text-principal shadow-sm"
                  : "text-slate-600 hover:text-slate-900"
              }`}
            >
              Consultas (3)
            </button>
            <button
              id="aba-internacoes"
              type="button"
              role="tab"
              aria-selected={abaAtiva === "internacoes"}
              aria-controls="painel-internacoes"
              onClick={() => setAbaAtiva("internacoes")}
              className={`rounded-lg px-4 py-2 font-semibold transition-colors ${
                abaAtiva === "internacoes"
                  ? "bg-white text-principal shadow-sm"
                  : "text-slate-600 hover:text-slate-900"
              }`}
            >
              Internações (2)
            </button>
          </div>

          <div
            id="painel-consultas"
            role="tabpanel"
            aria-labelledby="aba-consultas"
            hidden={abaAtiva !== "consultas"}
            className="flex flex-col gap-3"
          >
            <HistoryEntry
              title="Dr. Carlos Sales"
              status="Concluída"
              statusAtendimento="concluida"
              details={["14:00", "02/10/2026"]}
              to="/admin/consultas/1?origem=historico"
            />
            <HistoryEntry
              title="Dra. Juliana Mendes"
              status="Cancelada"
              statusAtendimento="cancelada"
              details={["09:15", "18/09/2026"]}
              to="/admin/consultas/2?origem=historico"
            />
            <HistoryEntry
              title="Dr. Carlos Sales"
              status="Concluída"
              statusAtendimento="concluida"
              details={["15:30", "05/08/2026"]}
              to="/admin/consultas/3?origem=historico"
            />
          </div>

          <div
            id="painel-internacoes"
            role="tabpanel"
            aria-labelledby="aba-internacoes"
            hidden={abaAtiva !== "internacoes"}
            className="flex flex-col gap-3"
          >
            <HistoryEntry
              title="Quarto 101 - Leito 1"
              status="Em andamento"
              statusAtendimento="emAndamento"
              details={["Entrada 05/10/2026"]}
            />
            <HistoryEntry
              title="Quarto 201 - Leito 1"
              status="Alta"
              statusAtendimento="concluida"
              details={["Entrada 10/03/2026", "Alta 14/03/2026"]}
            />
          </div>
        </section>
      </div>
    </div>
  );
}

export default DetalhesPaciente;
