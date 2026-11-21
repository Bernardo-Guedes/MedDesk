interface SeletorOpcoesProps<T extends string> {
  nome: string;
  rotulo: string;
  valor: T;
  onChange: (valor: T) => void;
  opcoes: { valor: T; texto: string }[];
}

function SeletorOpcoes<T extends string>({ nome, rotulo, valor, onChange, opcoes }: SeletorOpcoesProps<T>) {
  return (
    <fieldset>
      <legend className="mb-3 text-lg font-semibold text-principal">{rotulo}</legend>
      <div className="flex flex-wrap gap-1 rounded-xl bg-background p-1">
        {opcoes.map((opcao) => (
          <label key={opcao.valor} className={`flex-1 cursor-pointer rounded-lg px-3 py-2 text-center font-semibold transition-colors ${valor === opcao.valor ? "bg-white text-principal shadow-sm" : "text-slate-700 hover:bg-white/60"} focus-within:ring-2 focus-within:ring-destaque`}>
            <input
              type="radio"
              name={nome}
              value={opcao.valor}
              checked={valor === opcao.valor}
              onChange={() => onChange(opcao.valor)}
              className="sr-only"
            />
            {opcao.texto}
          </label>
        ))}
      </div>
    </fieldset>
  );
}

export default SeletorOpcoes;
