import { useState, type FormEvent } from "react";
import { Stethoscope } from "lucide-react";
import { useNavigate } from "react-router";
import { DangerButton, PrimaryButton } from "@/components/ui/button";

// Dados de exemplo de pacientes, datas e horários disponíveis.
const pacientes = [
  "Alice Martins",
  "Luiza Gomes",
  "Monark Igor Power Da Silva",
  "Pedro Alves",
];
const datas = [
  { valor: "2026-10-05", texto: "05/10/2026 (Segunda-feira)" },
  { valor: "2026-10-06", texto: "06/10/2026 (Terça-feira)" },
  { valor: "2026-10-07", texto: "07/10/2026 (Quarta-feira)" },
];
const horarios = ["10:00", "10:45", "19:00"];

const classeCampo =
  "w-full rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 text-base text-slate-700 outline-none focus:border-principal focus:ring-2 focus:ring-destaque";
const classeRotulo = "text-sm font-semibold text-slate-700";

function NovaConsulta() {
  const navigate = useNavigate();
  const [mensagem, setMensagem] = useState("");

  const confirmar = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMensagem("Não foi possível agendar: a integração com o servidor ainda não está disponível.");
  };

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <Stethoscope size={40} aria-hidden="true" />
        <h1>Agendar Nova Consulta</h1>
      </div>
      <div className="mx-auto max-w-4xl p-5 md:p-8">
        <section className="rounded-2xl bg-white p-6 shadow-sm md:p-8">
          <h2 className="mb-6 text-xl font-semibold text-principal">
            Detalhes do Agendamento
          </h2>
          <form onSubmit={confirmar} className="flex flex-col gap-5">
            <div className="flex flex-col gap-2">
              <label htmlFor="paciente-consulta" className={classeRotulo}>Paciente</label>
              <input
                id="paciente-consulta"
                list="pacientes-consulta"
                type="text"
                placeholder="Pesquise pelo nome ou selecione na lista..."
                className={classeCampo}
                required
              />
              <datalist id="pacientes-consulta">
                {pacientes.map((paciente) => <option key={paciente} value={paciente} />)}
              </datalist>
            </div>
            <div className="grid gap-5 sm:grid-cols-2">
              <div className="flex flex-col gap-2">
                <label htmlFor="data-nova-consulta" className={classeRotulo}>Data da Consulta</label>
                <select id="data-nova-consulta" className={classeCampo} defaultValue="" required>
                  <option value="" disabled>Selecione uma data</option>
                  {datas.map((data) => <option key={data.valor} value={data.valor}>{data.texto}</option>)}
                </select>
              </div>
              <div className="flex flex-col gap-2">
                <label htmlFor="horario-nova-consulta" className={classeRotulo}>Horário</label>
                <select id="horario-nova-consulta" className={classeCampo} defaultValue="" required>
                  <option value="" disabled>Selecione um horário</option>
                  {horarios.map((horario) => <option key={horario} value={horario}>{horario}</option>)}
                </select>
              </div>
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="motivo-nova-consulta" className={classeRotulo}>Motivo da Consulta</label>
              <input
                id="motivo-nova-consulta"
                type="text"
                placeholder="Ex: Avaliação de rotina, retorno de exames, febre contínua..."
                className={classeCampo}
                required
              />
            </div>
            <div className="flex flex-col gap-2">
              <label htmlFor="observacoes-nova-consulta" className={classeRotulo}>Observações Médicas</label>
              <textarea
                id="observacoes-nova-consulta"
                rows={4}
                placeholder="Adicione notas de pré-consulta, preparações ou lembretes..."
                className={`${classeCampo} resize-y`}
              />
            </div>
            {mensagem && <p role="alert" className="text-sm text-red-700">{mensagem}</p>}
            <div className="flex flex-wrap justify-end gap-3 border-t border-slate-200 pt-5">
              <DangerButton texto="Cancelar" onClick={() => navigate("/profissional-saude/agenda")} />
              <PrimaryButton texto="Confirmar Agendamento" submit />
            </div>
          </form>
        </section>
      </div>
    </div>
  );
}

export default NovaConsulta;
