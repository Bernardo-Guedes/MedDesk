import { useState, type FormEvent } from "react";
import { useNavigate } from "react-router";
import { DangerButton, PrimaryButton } from "./ui/button";

type TipoAtendimento = "rotina" | "especializado" | "emergencia" | "cirurgia" | "internacao";

const tipos: { valor: TipoAtendimento; texto: string }[] = [
  { valor: "rotina", texto: "Rotina" },
  { valor: "especializado", texto: "Especializado" },
  { valor: "emergencia", texto: "Emergência" },
  { valor: "cirurgia", texto: "Cirurgia" },
  { valor: "internacao", texto: "Internação" },
];

// Dados de exemplo para o formulário.
const pacientes = ["Alice Martins", "Luiza Gomes", "Monark Igor Power Da Silva", "Pedro Alves"];
const profissionais = ["Dr. Carlos Sales", "Dr. Roberto Almeida", "Dr. Augusto Faria", "Dra. Marina Costa"];
const especialidades = ["Pediatria", "Ortopedia"];
const quartos = ["102", "202", "302"];

const campo = "w-full rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 text-base text-slate-700 outline-none focus:border-principal focus:ring-2 focus:ring-destaque";
const rotulo = "text-sm font-semibold text-slate-700";

function FormAtendimento() {
  const [tipo, setTipo] = useState<TipoAtendimento>("rotina");
  const [mensagem, setMensagem] = useState("");
  const navigate = useNavigate();

  const criar = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMensagem("Não foi possível criar o atendimento: a integração com o servidor ainda não está disponível.");
  };

  return (
    <div className="mx-auto mt-8 max-w-3xl rounded-2xl bg-white p-5 shadow-sm md:p-8">
      <form className="flex flex-col gap-5" onSubmit={criar}>
        <fieldset>
          <legend className="mb-3 text-lg font-semibold text-principal">Tipo de Atendimento</legend>
          <div className="flex flex-wrap gap-1 rounded-xl bg-background p-1">
            {tipos.map((opcao) => (
              <label key={opcao.valor} className={`flex-1 cursor-pointer rounded-lg px-3 py-2 text-center font-semibold transition-colors ${tipo === opcao.valor ? "bg-white text-principal shadow-sm" : "text-slate-700 hover:bg-white/60"} focus-within:ring-2 focus-within:ring-destaque`}>
                <input
                  type="radio"
                  name="tipoAtendimento"
                  value={opcao.valor}
                  checked={tipo === opcao.valor}
                  onChange={() => {
                    setTipo(opcao.valor);
                    setMensagem("");
                  }}
                  className="sr-only"
                />
                {opcao.texto}
              </label>
            ))}
          </div>
        </fieldset>

        <div className="flex flex-col gap-2">
          <label htmlFor="paciente-atendimento" className={rotulo}>Paciente</label>
          <input id="paciente-atendimento" list="pacientes-atendimento" placeholder="Digite para pesquisar ou selecione o paciente..." className={campo} required />
          <datalist id="pacientes-atendimento">
            {pacientes.map((paciente) => <option key={paciente} value={paciente} />)}
          </datalist>
        </div>

        <div className="flex flex-col gap-2">
          <label htmlFor="profissional-atendimento" className={rotulo}>Profissional Responsável</label>
          <input id="profissional-atendimento" list="profissionais-atendimento" placeholder="Digite para pesquisar o profissional..." className={campo} required />
          <datalist id="profissionais-atendimento">
            {profissionais.map((profissional) => <option key={profissional} value={profissional} />)}
          </datalist>
        </div>

        {tipo === "especializado" && (
          <div className="flex flex-col gap-2">
            <label htmlFor="especialidade-atendimento" className={rotulo}>Especialidade</label>
            <select id="especialidade-atendimento" defaultValue="" className={campo} required>
              <option value="" disabled>Selecione a especialidade</option>
              {especialidades.map((especialidade) => <option key={especialidade} value={especialidade}>{especialidade}</option>)}
            </select>
          </div>
        )}

        {tipo === "internacao" ? (
          <>
            <div className="grid gap-5 sm:grid-cols-2">
              <div className="flex flex-col gap-2">
                <label htmlFor="quarto-atendimento" className={rotulo}>Quarto</label>
                <select id="quarto-atendimento" defaultValue="" className={campo} required>
                  <option value="" disabled>Selecione o quarto disponível</option>
                  {quartos.map((quarto) => <option key={quarto} value={quarto}>Quarto {quarto}</option>)}
                </select>
              </div>
              <div className="flex flex-col gap-2">
                <label htmlFor="data-entrada" className={rotulo}>Data de Entrada</label>
                <input id="data-entrada" type="date" className={campo} required />
              </div>
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="data-alta" className={rotulo}>Data de Alta</label>
              <input id="data-alta" type="date" className={campo} />
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="observacoes-internacao" className={rotulo}>Observações</label>
              <textarea id="observacoes-internacao" rows={3} className={`${campo} resize-y`} placeholder="Informações relevantes sobre a internação, restrições ou cuidados especiais..." />
            </div>
          </>
        ) : (
          <>
            <div className="grid gap-5 sm:grid-cols-2">
              <div className="flex flex-col gap-2">
                <label htmlFor="data-atendimento" className={rotulo}>Data</label>
                <input id="data-atendimento" type="date" className={campo} required />
              </div>
              <div className="flex flex-col gap-2">
                <label htmlFor="horario-atendimento" className={rotulo}>Horário</label>
                <input id="horario-atendimento" type="time" className={campo} required />
              </div>
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="motivo-atendimento" className={rotulo}>Motivo do Atendimento</label>
              <input id="motivo-atendimento" type="text" placeholder="Ex: avaliação de rotina, dor de cabeça, retorno de exames..." className={campo} required />
            </div>
          </>
        )}
        {mensagem && <p role="alert" className="text-sm text-red-700">{mensagem}</p>}
        <div className="flex flex-wrap justify-end gap-3 border-t border-slate-200 pt-5">
          <DangerButton texto="Cancelar" onClick={() => navigate("/admin")} />
          <PrimaryButton texto="Criar atendimento" submit />
        </div>
      </form>
    </div>
  );
}

export default FormAtendimento;
