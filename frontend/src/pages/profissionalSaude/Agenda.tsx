import { useState } from "react";
import { CalendarDays } from "lucide-react";
import { Link } from "react-router";
import ListItem from "@/components/ListItem";

// Dados de exemplo das consultas agendadas.
const consultas = [
  { id: 1, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
  { id: 2, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
  { id: 3, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
  { id: 4, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
  { id: 5, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
  { id: 6, paciente: "Monark Igor Power Da Silva", data: "2026-12-25", horario: "19:00" },
];

function Agenda() {
  const [mes, setMes] = useState("2026-12");
  const consultasFiltradas = consultas.filter((consulta) =>
    mes ? consulta.data.slice(0, 7) === mes : true,
  );

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <CalendarDays size={40} aria-hidden="true" />
        <h1>Agenda</h1>
      </div>
      <div className="mx-auto max-w-5xl p-5 md:p-8">
        <div className="mb-6 flex flex-wrap items-center justify-end gap-3">
          <label htmlFor="filtro-mes" className="font-semibold text-slate-700">
            Filtro:
          </label>
          <input
            id="filtro-mes"
            type="month"
            value={mes}
            onChange={(event) => setMes(event.target.value)}
            className="rounded-full border border-destaque bg-white px-4 py-2 text-slate-700 focus:outline-none focus:ring-2 focus:ring-destaque"
          />
        </div>
        <section aria-label="Consultas agendadas" className="flex flex-col gap-4">
          {consultasFiltradas.length > 0 ? (
            consultasFiltradas.map((consulta) => (
              <Link key={consulta.id} to={`/profissional-saude/consultas/${consulta.id}`} className="block rounded-3xl focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-principal sm:rounded-full">
                <ListItem
                  nome={consulta.paciente}
                  detalhes={[consulta.horario, `Dia ${consulta.data.slice(8, 10)}`]}
                  imagem={null}
                  compacto
                />
              </Link>
            ))
          ) : (
            <p role="status" className="rounded-xl bg-white p-5 text-slate-600">
              Nenhuma consulta encontrada para o mês selecionado.
            </p>
          )}
        </section>
      </div>
    </div>
  );
}

export default Agenda;
