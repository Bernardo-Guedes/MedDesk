import { useState } from "react";
import { ClipboardList } from "lucide-react";
import ListItem from "@/components/ListItem";
import SelectFiltro from "@/components/ui/SelectFiltro";
import SeletorOpcoes from "@/components/ui/SeletorOpcoes";
import StatusAtendimento, { type TipoStatusAtendimento } from "@/components/ui/StatusAtendimento";

type Tipo = "Rotina" | "Especializado" | "Emergência" | "Cirurgia" | "Internação";
type Situacao =
  | "Agendado"
  | "Confirmado"
  | "Em atendimento"
  | "Em andamento"
  | "Concluído"
  | "Cancelado"
  | "Não compareceu"
  | "Aguardando avaliação"
  | "Internado"
  | "Alta";
type Prioridade = "Crítica" | "Alta" | "Moderada" | "Baixa";

type Atendimento = {
  id: number;
  paciente: string;
  profissional: string;
  data: string;
  horario: string;
  situacao: Situacao;
} & (
  | { tipo: "Emergência"; prioridade: Prioridade }
  | { tipo: Exclude<Tipo, "Emergência">; prioridade?: never }
);

const tipos: { valor: Tipo; texto: string }[] = [
  { valor: "Rotina", texto: "Rotina" },
  { valor: "Especializado", texto: "Especializado" },
  { valor: "Emergência", texto: "Emergência" },
  { valor: "Cirurgia", texto: "Cirurgia" },
  { valor: "Internação", texto: "Internação" },
];

const situacoesPorTipo: Record<Tipo, Situacao[]> = {
  Rotina: ["Agendado", "Confirmado", "Em atendimento", "Concluído", "Cancelado", "Não compareceu"],
  Especializado: ["Agendado", "Confirmado", "Em atendimento", "Concluído", "Cancelado", "Não compareceu"],
  Emergência: ["Aguardando avaliação", "Em atendimento", "Concluído"],
  Cirurgia: ["Agendado", "Em andamento", "Concluído", "Cancelado"],
  Internação: ["Internado", "Alta"],
};

const estilosSituacao: Record<Situacao, TipoStatusAtendimento> = {
  Agendado: "agendada",
  Confirmado: "agendada",
  "Em atendimento": "emAndamento",
  "Em andamento": "emAndamento",
  Concluído: "concluida",
  Cancelado: "cancelada",
  "Não compareceu": "naoCompareceu",
  "Aguardando avaliação": "neutro",
  Internado: "emAndamento",
  Alta: "concluida",
};

const ordemPrioridade: Record<Prioridade, number> = {
  Crítica: 0,
  Alta: 1,
  Moderada: 2,
  Baixa: 3,
};

// Atendimentos de exemplo; a página ainda não consome a API.
const atendimentos: Atendimento[] = [
  { id: 1, paciente: "Alice Martins", profissional: "Dra. Marina Costa", tipo: "Rotina", situacao: "Agendado", data: "2026-11-25", horario: "09:00" },
  { id: 2, paciente: "Luiza Gomes", profissional: "Dr. Carlos Sales", tipo: "Especializado", situacao: "Confirmado", data: "2026-11-26", horario: "10:00" },
  { id: 3, paciente: "Pedro Alves", profissional: "Dra. Marina Costa", tipo: "Emergência", situacao: "Aguardando avaliação", prioridade: "Baixa", data: "2026-11-20", horario: "09:00" },
  { id: 4, paciente: "Mariana Costa", profissional: "Dr. Roberto Almeida", tipo: "Emergência", situacao: "Em atendimento", prioridade: "Crítica", data: "2026-11-20", horario: "11:00" },
  { id: 5, paciente: "João Pereira", profissional: "Dr. Carlos Sales", tipo: "Emergência", situacao: "Aguardando avaliação", prioridade: "Alta", data: "2026-11-20", horario: "10:30" },
  { id: 6, paciente: "Ana Oliveira", profissional: "Dra. Marina Costa", tipo: "Emergência", situacao: "Aguardando avaliação", prioridade: "Alta", data: "2026-11-20", horario: "10:00" },
  { id: 7, paciente: "Beatriz Souza", profissional: "Dr. Carlos Sales", tipo: "Emergência", situacao: "Concluído", prioridade: "Moderada", data: "2026-11-20", horario: "08:00" },
  { id: 8, paciente: "Ricardo Lima", profissional: "Dr. Augusto Faria", tipo: "Cirurgia", situacao: "Agendado", data: "2026-11-27", horario: "08:00" },
  { id: 9, paciente: "Clara Santos", profissional: "Dra. Marina Costa", tipo: "Internação", situacao: "Internado", data: "2026-11-19", horario: "14:00" },
  { id: 10, paciente: "Miguel Rocha", profissional: "Dr. Roberto Almeida", tipo: "Internação", situacao: "Alta", data: "2026-11-18", horario: "15:00" },
  { id: 11, paciente: "Laura Ferreira", profissional: "Dra. Marina Costa", tipo: "Rotina", situacao: "Concluído", data: "2026-11-19", horario: "11:00" },
  { id: 12, paciente: "Carla Andrade", profissional: "Dr. Augusto Faria", tipo: "Cirurgia", situacao: "Em andamento", data: "2026-11-20", horario: "09:00" },
  { id: 13, paciente: "Paulo Moreira", profissional: "Dr. Augusto Faria", tipo: "Cirurgia", situacao: "Concluído", data: "2026-11-19", horario: "10:00" },
  { id: 14, paciente: "Sofia Almeida", profissional: "Dr. Augusto Faria", tipo: "Cirurgia", situacao: "Cancelado", data: "2026-11-21", horario: "14:00" },
];

function Atendimentos() {
  const [tipo, setTipo] = useState<Tipo>("Rotina");
  const [situacao, setSituacao] = useState("");
  const [prioridade, setPrioridade] = useState("");

  const situacoes = situacoesPorTipo[tipo];

  const filtrados = atendimentos
    .filter((atendimento) =>
      atendimento.tipo === tipo &&
      (!situacao || atendimento.situacao === situacao) &&
      (!prioridade || (atendimento.tipo === "Emergência" && atendimento.prioridade === prioridade)),
    )
    .sort((a, b) => {
      if (a.tipo === "Emergência" && b.tipo === "Emergência") {
        return ordemPrioridade[a.prioridade] - ordemPrioridade[b.prioridade]
          || a.data.localeCompare(b.data)
          || a.horario.localeCompare(b.horario);
      }
      return 0;
    });

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <ClipboardList size={40} aria-hidden="true" />
        <h1>Atendimentos</h1>
      </div>
      <div className="mx-5 my-8 md:mx-8">
        <div className="mb-6 flex flex-col gap-4 rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
          <SeletorOpcoes
            nome="tipo-atendimento"
            rotulo="Tipo de atendimento"
            valor={tipo}
            onChange={(valor) => {
              setTipo(valor);
              setSituacao("");
              setPrioridade("");
            }}
            opcoes={tipos}
          />
          <div className="flex flex-wrap gap-4">
            <div className="w-full sm:w-auto">
              <SelectFiltro
                id="situacao-atendimento"
                rotulo="Situação"
                valor={situacao}
                onChange={setSituacao}
                opcoes={[
                  { valor: "", texto: "Todas as situações" },
                  ...situacoes.map((item) => ({ valor: item, texto: item })),
                ]}
              />
            </div>
            {tipo === "Emergência" && (
              <div className="w-full sm:w-auto">
                <SelectFiltro
                  id="prioridade-emergencia"
                  rotulo="Prioridade"
                  valor={prioridade}
                  onChange={setPrioridade}
                  opcoes={[
                    { valor: "", texto: "Todas as prioridades" },
                    ...Object.keys(ordemPrioridade).map((item) => ({ valor: item, texto: item })),
                  ]}
                />
              </div>
            )}
          </div>
        </div>
        {filtrados.length ? (
          <ul className="flex flex-col gap-3" aria-label="Lista de atendimentos">
            {filtrados.map((atendimento) => (
              <li key={atendimento.id}>
                <ListItem
                  nome={atendimento.paciente}
                  cargo={`${atendimento.tipo} - ${atendimento.profissional}`}
                  imagem={null}
                  compacto
                  destaque={<StatusAtendimento texto={atendimento.situacao} status={estilosSituacao[atendimento.situacao]} />}
                  detalhes={[
                    `${atendimento.data.split("-").reverse().join("/")} às ${atendimento.horario}`,
                    ...(atendimento.tipo === "Emergência" ? [`Prioridade: ${atendimento.prioridade}`] : []),
                  ]}
                />
              </li>
            ))}
          </ul>
        ) : (
          <p role="status" className="rounded-xl bg-white p-5 text-slate-600">
            Nenhum atendimento encontrado para os filtros selecionados.
          </p>
        )}
      </div>
    </div>
  );
}

export default Atendimentos;
